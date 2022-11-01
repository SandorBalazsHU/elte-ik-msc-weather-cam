package com.example.weatherapp.data.hardware

import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.HwHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.*

class MeasurementsSource(private val client: HttpClient = HwHttpClient) {
    suspend fun getMeasurements() : HwMeasurementEntity? = withContext(Dispatchers.IO){
        client.getMeasurements()
    }

    private suspend fun HttpClient.getMeasurements(): HwMeasurementEntity? {
        //workaround for wrong content-type
        //it should be json, but it is text
        val endpoint = "http://mock.weather.s-b-x.com"
        return try {
            val res : HwMeasurementEntity =
                client.get(endpoint){
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Any)
                }.body()
            res
        } catch (e : NoTransformationFoundException) {
            val entString: String = client.get(endpoint).body()
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            json.decodeFromString(entString)
        }
    }
}