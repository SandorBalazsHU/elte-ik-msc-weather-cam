package com.example.weatherapp.client

import android.os.SystemClock
import android.util.Log
import com.example.weatherapi.data.api.MeasurementsApi
import com.example.weatherapi.data.api.PicturesApi
import com.example.weatherapi.data.api.StatusApi
import com.example.weatherapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.HwMeasurementsEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.toImmutableList
import org.openapitools.client.infrastructure.ApiClient


class StationClient(
    private val measurementsRepository: MeasurementsRepository = MeasurementsRepository(),
    private val apiClient: ApiClient = ApiClient()
) {

    private val measurementService = apiClient.createService(MeasurementsApi::class.java)
    private val picturesService = apiClient.createService(PicturesApi::class.java)
    private val statusService = apiClient.createService(StatusApi::class.java)

    fun setApiKey(key: String): Boolean {
        return try {
            apiClient.setBearerToken(key)
            true
        } catch (ex: Exception){
            Log.e("WeatherStationClient", "Could not set api key", ex)
            false
        }
    }

    /*
    *  Poll the hardware units, send the result to the api server.
    * Returns false if one of the hardware units did not respond correctly.
    * */
    suspend fun addMeasurements(addresses: Map<String, HardwareEntity>) = withContext(Dispatchers.IO) {
        val results: MutableList<MeasurementEntity> = mutableListOf()
        var hasError = false
        for ((address, _) in addresses) {
            when(val res = getMeasurement(address)) {
                is ClientResult.Success -> {
                    if (res.res != null){
                        results.add(res.res)
                    }
                }
                else -> {
                    hasError = true
                    Log.e("WeatherStationClient",
                        "Misc. error for hardware address $address")
                }
            }
        }
        runClientCatching {
            measurementService.addMeasurements(results.toImmutableList())
        }
        (!hasError)
    }

    suspend fun addPicture(file: java.io.File) = withContext(Dispatchers.IO) {
        runClientCatching {
            Log.d("WeatherStationClient", "RUN CLIENT")
            val bytes = file.readBytes()
            val body =
                bytes.toRequestBody("application/octet-stream".toMediaTypeOrNull(), 0, bytes.size)
            val res = picturesService.addPicture("application/octet-stream", body)
            Log.d("WeatherStationClient", "add picture code: ${res.code()}")
            res
        }
        Unit
    }

    private suspend fun addStatus(status: Int): ClientResult<Int> = withContext(Dispatchers.IO) {
        runClientCatching {
            val res = statusService.addStatus(status)
            res.code()
        }
    }

    private suspend fun getMeasurement(address: String) : ClientResult<MeasurementEntity?> =
        runClientCatching {
            val hwEnt = measurementsRepository.measurements(address)
            hwEnt?.let { translateMeasurement(it) }
        }


    private fun translateMeasurement(hwEnt: HwMeasurementsEntity) : MeasurementEntity =
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

suspend inline fun <R> runClientCatching(noinline block: suspend CoroutineScope.() -> R): ClientResult<R> =
    try {
        val res = withTimeout( 10*1000L, block)
        ClientResult.Success(res)
    } catch (cancellationException: CancellationException) {
        //avoid interference with coroutines
        throw cancellationException
    } catch (e: Throwable){
        Log.e("WeatherStationClient", "request error", e)
        ClientResult.Error(e)
    }

