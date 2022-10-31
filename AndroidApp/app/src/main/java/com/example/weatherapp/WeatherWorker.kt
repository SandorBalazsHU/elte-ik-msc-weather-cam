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


class MeasurementWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters)
{
    private val notificationProvider = WeatherNotification(appContext)
    private val measurementsRepository by lazy {
        MeasurementsRepository()
    }
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
    }

    override suspend fun doWork(): Result {
        val apiKey = inputData.getString(API_KEY) ?: return Result.failure()
        //val hwAddress = inputData.getString(ADDRESS) ?: return Result.failure()

        return try {
            val hwEnt = measurementsRepository.measurements()
            val ent = translateMeasurement(hwEnt)
            webApi.setApiKey(apiKey)
            webApi.addMeasurements(listOf(ent))
            notificationProvider.updateNotification(ent)
            Result.success()
        } catch (ex : Exception){
            Result.failure()
        }
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
        temperature = hwEnt.temperature,
        pressure = hwEnt.pressure,
        humidity = hwEnt.humidity
    )