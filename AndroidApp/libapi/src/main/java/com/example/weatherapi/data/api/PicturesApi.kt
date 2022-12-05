package com.example.weatherapi.data.api

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.example.weatherapi.data.entities.ApiResponseEntity

import okhttp3.MultipartBody

import okhttp3.ResponseBody

interface PicturesApi {
    /**
     * Add picture to server.
     * ## Functionality:  Upload picture to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key.    --- 
     * Responses:
     *  - 201: __Successfully__ uploaded the requested picture.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authenticate request with the provided API key. *Possible causes*:   - API key is invalid. 
     *
     * @return [ApiResponseEntity]
     */
    @POST("stations/pictures/")
    suspend fun addPicture(@Header("Content-Type") mime: String,
                           @Body body: RequestBody):
            Response<ApiResponseEntity>

}
