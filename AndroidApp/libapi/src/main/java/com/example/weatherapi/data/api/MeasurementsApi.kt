package com.example.weatherapi.data.api

import retrofit2.http.*
import retrofit2.Response

import com.example.weatherapi.data.entities.ApiResponseEntity
import com.example.weatherapi.data.entities.MeasurementEntity

interface MeasurementsApi {
    /**
     * Add new measurements to server
     * ## Functionality:  Add __multiple__ measurements a station to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 
     * Responses:
     *  - 201: __Successfully__ uploaded new measurements to the server.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *
     * @param measurementEntity **Important**:   New measurements sent to the server do **not** contain a unique identifiers station_id and measurement_id like the ones recieved by the server.   Unique identifiers are assigned by the server upon processing the measurements. 
     * @return [ApiResponseEntity]
     */
    @POST("stations/measurements")
    suspend fun addMeasurements(@Body measurementEntity: kotlin.collections.List<MeasurementEntity>): Response<ApiResponseEntity>

}
