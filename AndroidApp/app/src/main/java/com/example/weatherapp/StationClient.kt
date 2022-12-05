package com.example.weatherapp

import android.os.SystemClock
import android.util.Log
import com.example.weatherapi.data.api.MeasurementsApi
import com.example.weatherapi.data.api.PicturesApi
import com.example.weatherapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import org.openapitools.client.infrastructure.ApiClient
import java.io.IOException


class StationClient(
    private val measurementsRepository: MeasurementsRepository = MeasurementsRepository(),
    private val apiClient: ApiClient = ApiClient()
) {

    private val measurementService = apiClient.createService(MeasurementsApi::class.java)
    private val picturesService = apiClient.createService(PicturesApi::class.java)

    fun setApiKey(key: String): Boolean {
        return try {
            apiClient.setBearerToken(key)
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
                         else -> {
                             Log.e("WeatherStationClient",
                                 "Misc. error for hardware address ${it.first}")
                             null
                         }
                     }
                 }

             runClientCatching {
                 val res = measurementService.addMeasurements(goodMeasurements)
                 res.code()
             }
    }

    suspend fun addPicture(file: java.io.File): ClientResult<Int> = withContext(Dispatchers.IO) {
        runClientCatching {
            Log.d("WeatherStationClient", "RUN CLIENT")
            val bytes = file.readBytes()
            val body =
                bytes.toRequestBody("application/octet-stream".toMediaTypeOrNull(), 0, bytes.size)
            val res = picturesService.addPicture("application/octet-stream", body)
            Log.d("WeatherStationClient", "add picture code: ${res.code()}")
            res.code()
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
            humidity = hwEnt.humidity,
            timestamp = SystemClock.elapsedRealtime(),
            battery = -1f //TODO battery manager
        )
}

sealed class ClientResult<out T> {
    data class Success<T>(val res: T): ClientResult<T>()

    data class Error(val err: Throwable): ClientResult<Nothing>()
}

inline fun <R> runClientCatching(block: () -> R): ClientResult<R> =
    try {
        val res = block()
        ClientResult.Success(res)
    } catch (cancellationException: CancellationException) {
        //avoid interference with coroutines
        throw cancellationException
    } catch (e: Throwable){
        ClientResult.Error(e)
    }

