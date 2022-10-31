package com.example.weatherapp.data.hardware

import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.HwHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeasurementsSource(private val client: HttpClient = HwHttpClient) {
    suspend fun getMeasurements() : HwMeasurementEntity = withContext(Dispatchers.IO){
        client.getMeasurements()
    }

    //TODO: handle exceptions
    private suspend fun HttpClient.getMeasurements(): HwMeasurementEntity {
        val res : HwMeasurementEntity =
            client.get("http://mock.weather.s-b-x.com").body()
        return res
    }
}