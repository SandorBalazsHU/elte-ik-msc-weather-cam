package com.example.weatherapp.client

import android.os.SystemClock
import android.util.Log
import com.example.weatherapi.data.api.MeasurementsApi
import com.example.weatherapi.data.api.PicturesApi
import com.example.weatherapi.data.api.StatusApi
import com.example.weatherapi.data.entities.ApiResponseEntity
import com.example.weatherapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.HwMeasurementsEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.get
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.toImmutableList
import org.openapitools.client.infrastructure.ApiClient
import retrofit2.Response


class StationClient(
    private val measurementsRepository: MeasurementsRepository = MeasurementsRepository(),
    private val apiClient: ApiClient = ApiClient(authNames = arrayOf("apiKeyAuth"))
) {

    private val measurementService = apiClient.createService(MeasurementsApi::class.java)
    private val picturesService = apiClient.createService(PicturesApi::class.java)
    private val statusService = apiClient.createService(StatusApi::class.java)

    fun setApiKey(key: String) {
        try {
            apiClient.setBearerToken(key)
        } catch (ex: Exception){
            Log.e("WeatherStationClient", "Could not set api key", ex)
        }
    }

    /*
    * Poll the hardware units, send the result to the api server.
    * Returns false if one of the hardware units did not respond correctly.
    * */
    suspend fun addMeasurements(addresses: Map<String, HardwareEntity>): Pair<Boolean, Int?> = withContext(Dispatchers.IO) {
        val results: MutableList<MeasurementEntity> = mutableListOf()
        var wasSuccess = true
        for ((name, ent) in addresses) {
            when(val res = getMeasurement(ent.ipAddress)) {
                is ClientResult.Success -> {
                    if (res.res != null){
                        results.add(res.res)
                    }
                }
                else -> {
                    wasSuccess = false
                    Log.e("WeatherStationClient",
                        "Misc. error for hardware named $name")
                }
            }
        }

        if(results.isNotEmpty()){
            runClientCatching {
                measurementService.addMeasurements(results.toImmutableList())
            }.let { wasSuccess to getCode(it) }
        } else {
            wasSuccess to 200
        }

    }

    suspend fun addPicture(file: java.io.File): Int? = withContext(Dispatchers.IO) {
        runClientCatching {
            Log.d("WeatherStationClient", "RUN CLIENT")
            val bytes = file.readBytes()
            val body =
                bytes.toRequestBody("application/octet-stream".toMediaTypeOrNull(), 0, bytes.size)
            val res = picturesService.addPicture("application/octet-stream", body)
            Log.d("WeatherStationClient", "add picture code: ${res.code()}")
            res
        }.let { getCode(it) }
    }

    suspend fun addStatus(status: Int): Int? = withContext(Dispatchers.IO) {
        runClientCatching {
            statusService.addStatus(status)
        }.let { getCode(it) }
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
            battery = hwEnt.battery
        )
}

sealed class ClientResult<out T> {
    data class Success<T>(val res: T): ClientResult<T>()

    data class Error(val err: Exception): ClientResult<Nothing>()

}

fun getCode(res: ClientResult<Response<ApiResponseEntity>>): Int? =
    when(res) {
        is ClientResult.Success ->
            res.res.code()
        else ->
            null
    }

suspend inline fun <R> runClientCatching(noinline block: suspend CoroutineScope.() -> R): ClientResult<R> =
    try {
        val res = withTimeout( 10*1000L, block)
        ClientResult.Success(res)
    } catch (cancellationException: CancellationException) {
        //avoid interference with coroutines
        throw cancellationException
    } catch (e: Exception){
        Log.e("WeatherStationClient", "request error", e)
        ClientResult.Error(e)
    }

