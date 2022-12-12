package com.example.weatherapi.data.api

import retrofit2.http.*
import retrofit2.Response

import com.example.weatherapi.data.entities.ApiResponseEntity

interface StationsApi {
    /**
     * Update the status of the weather station.
     * ## Functionality:    Updates the status of the weather station.         ---   ### Prerequisites:   - This endpoint can only be used with a valid API key.   ---
     * Responses:
     *  - 200: __Successfully__ updated the status of the weather station.
     *  - 400: __Failed__ to update the status of the weather station.
     *  - 403: __Failed__ to authenticate request with the provided API key. *Possible causes*:   - API key is invalid.
     *
     * @param statusCode
     * @return [ApiResponseEntity]
     */
    @PUT("stations/status/{status_code}")
    suspend fun updateStationStatus(@Path("status_code") statusCode: kotlin.Int): Response<ApiResponseEntity>

}
