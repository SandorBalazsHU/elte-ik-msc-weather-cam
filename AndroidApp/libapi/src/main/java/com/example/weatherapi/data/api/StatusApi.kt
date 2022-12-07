package com.example.weatherapi.data.api

import com.example.weatherapi.data.entities.ApiResponseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StatusApi {
    @POST("stations/status")
    suspend fun addStatus(@Body measurementEntity: kotlin.Int): Response<ApiResponseEntity>
}