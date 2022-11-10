package com.example.weatherapp

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.libapi.data.api.MeasurementsApi
import com.example.libapi.data.entities.MeasurementEntity
import com.example.weatherapp.WeatherNotification.Companion.closeNotification
import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import io.ktor.client.engine.android.*
import kotlinx.coroutines.delay


class MeasurementWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters)
{
    private val notificationProvider = WeatherNotification(appContext)
    private val measurementsRepository by lazy {
        MeasurementsRepository()
    }
    //TODO actual auth
    private val webApi by lazy {
        MeasurementsApi(
            baseUrl = "http://mock.weather.s-b-x.com",
            httpClientEngine = Android.create {
                connectTimeout = 10_000
                socketTimeout = 10_000
                // proxy?
                // auth?
            }
        )
    }
    companion object {
        const val API_KEY = "api_key"
        //const val ADDRESS = "hw_address"
        const val ADDRESSES = "hw_addresses"
    }

    override suspend fun doWork(): Result {
        val apiKey = inputData.getString(API_KEY) ?: return Result.failure()
        val addresses = inputData.getStringArray(ADDRESSES) ?: return Result.failure()
        return try {
            addresses.filterNotNull().forEach {
                delay(1000L)
                pollAddress(it)
            }
            Result.success()
        } catch (ex : Exception) {
            notificationProvider.errorNotification(ex.stackTraceToString())
            Result.failure()
        }
    }

    private suspend fun pollAddress(address : String){
        val hwEnt = measurementsRepository.measurements(address)!!
        val ent = translateMeasurement(hwEnt)
        webApi.addMeasurements(listOf(ent))
        notificationProvider.updateNotification(ent)
    }

}

class TerminatingWorker(
    private val appContext: Context,
    workerParameters: WorkerParameters
) : Worker(appContext, workerParameters){
    override fun doWork(): Result {
        closeNotification(appContext)
        return Result.success()
    }
}

fun translateMeasurement(hwEnt : HwMeasurementEntity) : MeasurementEntity =
    MeasurementEntity(
        temperature = hwEnt.temp,
        pressure = hwEnt.pressure,
        humidity = hwEnt.humidity
    )