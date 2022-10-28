package com.example.weatherapp.data.hardware

import com.example.libapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.HwHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeasurementsSource(private val client: HttpClient = HwHttpClient) {
    suspend fun getMeasurements() : MeasurementEntity = withContext(Dispatchers.IO){
        client.getMeasurements()
    }

    private suspend fun HttpClient.getMeasurements(): MeasurementEntity {
        val res : MeasurementEntity =
            client.get("http://mock.weather.s-b-x.com").body()
        return res
    }
}