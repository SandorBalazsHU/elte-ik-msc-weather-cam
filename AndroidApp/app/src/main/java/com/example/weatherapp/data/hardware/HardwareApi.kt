package com.example.weatherapp.data.hardware

import retrofit2.Response
import retrofit2.http.GET

interface HardwareApi {

    @GET("/")
    suspend fun getMeasurements(): Response<HwMeasurementEntity>
}