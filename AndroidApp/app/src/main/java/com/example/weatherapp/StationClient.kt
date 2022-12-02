package com.example.weatherapp

import android.util.Log
import com.example.libapi.data.api.MeasurementsApi
import com.example.libapi.data.api.PicturesApi
import com.example.libapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import io.ktor.client.plugins.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

//auth????
class StationClient(
    private val measurementsRepository: MeasurementsRepository = MeasurementsRepository(),
    private val measurementsApi: MeasurementsApi = MeasurementsApi(),
    private val picturesApi: PicturesApi = PicturesApi()
) {

    fun setApiKey(key: String): Boolean {
        return try {
            //api key param name?
            //any other auth?
            measurementsApi.setApiKey(key)
            picturesApi.setApiKey(key)
            true
        } catch (ex: Exception){
            Log.e("WeatherStationClient", "Could not set api key", ex)
            false
        }
    }

    suspend fun pollMeasurements(addresses: Map<String, HardwareEntity>) = withContext(Dispatchers.IO) {
             val results = addresses.map {
                 it.key to getMeasurement(it.key)
             }
            //todo: warning for unreachable hardware units
             val goodMeasurements: List<MeasurementEntity>  =
                 results.mapNotNull {
                     when (val res = it.second) {
                         is ClientResult.Success ->
                             res.res
                         is ClientResult.Error.HttpError -> {
                             Log.e("WeatherStationClient",
                                 "Http error for hardware address ${it.first}: ${res.code}", res.body)
                             null
                         }
                         else -> {
                             Log.e("WeatherStationClient",
                                 "Misc. error for hardware address ${it.first}")
                             null
                         }
                     }
                 }

             runClientCatching {
                 val res = measurementsApi.addMeasurements(goodMeasurements)
                 res.status
             }
    }

    suspend fun addPicture(file: java.io.File): ClientResult<Int> = withContext(Dispatchers.IO) {
        runClientCatching {
            val res = picturesApi.addPicture(file)
            res.status
        }
    }

    private suspend fun getMeasurement(address: String) : ClientResult<MeasurementEntity?> =
        runClientCatching {
            val hwEnt = measurementsRepository.measurements(address)
            hwEnt?.let { translateMeasurement(it) }
        }


    private fun translateMeasurement(hwEnt: HwMeasurementEntity) : MeasurementEntity =
        MeasurementEntity(
            temperature = hwEnt.temp,
            pressure = hwEnt.pressure,
            humidity = hwEnt.humidity
        )
}

sealed class ClientResult<out T> {
    data class Success<T>(val res: T): ClientResult<T>()

    sealed class Error: ClientResult<Nothing>() {
        data class HttpError(val code: Int, val body: Throwable? = null) : Error()
        object NetworkError : Error()
        object SerializationError : Error()
    }
}

inline fun <R> runClientCatching(block: () -> R): ClientResult<R> =
    try {
        val res = block()
        ClientResult.Success(res)
    } catch (e: ClientRequestException) {
        ClientResult.Error.HttpError(e.response.status.value, e)
    } catch (e: ServerResponseException) {
        ClientResult.Error.HttpError(e.response.status.value, e)
    } catch (e: IOException) {
        ClientResult.Error.NetworkError
    } catch (e: SerializationException) {
        ClientResult.Error.SerializationError
    }

