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
     * @param body Server
     * @return [ApiResponseEntity]
     */
    @Multipart
    @POST("stations/pictures")
    suspend fun addPicture(@Part part: MultipartBody.Part): Response<ApiResponseEntity>

    /**
     * Find last picture.
     * ## Functionality:  Returns the __latest__ picture recieved from the weather station.   *Note*: The latest picture is defined as the last picture of station with station_id processed by the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 
     * Responses:
     *  - 200: __Successfully__ found the requested picture.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested picture. *Possible causes*:   - There are no pictures for this station on the server. 
     *
     * @param stationId station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
     * @return [ResponseBody]
     */
    @GET("stations/{station_id}/pictures/latest")
    suspend fun getLastPicture(@Path("station_id") stationId: java.util.UUID): Response<ResponseBody>

    /**
     * Find picture by id.
     * ## Functionality:    Returns a picture identified by picture_id of a station identified by station_id.      ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.      --- 
     * Responses:
     *  - 200: __Successfully__ found the requested picture.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested picture. *Possible causes*:   - Picture with given picture_id does not exist.   - Station with given station_id does not exist. 
     *
     * @param pictureId 
     * @param stationId station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
     * @return [ResponseBody]
     */
    @GET("stations/{station_id}/pictures/{picture_id}")
    suspend fun getPictureById(@Path("picture_id") pictureId: java.util.UUID, @Path("station_id") stationId: java.util.UUID): Response<ResponseBody>

    /**
     * Find picture relative to other picture.
     * ## Functionality:  Returns a __single__ picture relative to an other picture.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 
     * Responses:
     *  - 200: __Successfully__ found the requested picture.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested picture. *Possible causes*:   - Picture with given picture_id does not exist.   - Picture offset before or after picture identified by picture_id does not exist. 
     *
     * @param stationId station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
     * @param pictureId picture_id is a __unique__ identifier for pictures.  picture_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
     * @param offset offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below: | offset value | result | |---|---| | offset &lt; 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset &gt; 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset &#x3D; 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   | 
     * @return [ResponseBody]
     */
    @GET("stations/{station_id}/pictures")
    suspend fun getRelativePicture(@Path("station_id") stationId: java.util.UUID, @Query("picture_id") pictureId: java.util.UUID, @Query("offset") offset: kotlin.Long): Response<ResponseBody>

}
