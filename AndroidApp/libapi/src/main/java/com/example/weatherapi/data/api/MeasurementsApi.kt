package com.example.weatherapi.data.api

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.example.weatherapi.data.entities.ApiResponseEntity
import com.example.weatherapi.data.entities.IndexedMeasurementEntity
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

    /**
     * Find the latest measurement of a station.
     * ## Functionality:  Returns the __latest or the first__ measurement recieved from the weather station.   *Note*: The latest measurement is defined as the last measurement of station with station_id processed by the server. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Responses:
     *  - 200: __Successfully__ found the requested measurement.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested measurement. *Possible causes*:   - There are no measurements for this station on the server.    
     *
     * @param stationId station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 
     * @param relative 
     * @return [IndexedMeasurementEntity]
     */
    @GET("stations/{station_id}/measurements/{relative}")
    suspend fun getLatestStationMeasurement(@Path("station_id") stationId: kotlin.Long, @Path("relative") relative: kotlin.String): Response<IndexedMeasurementEntity>

    /**
     * Find a single measurement of a station.
     * ## Functionality:  Returns a measurement identified by measurement_id of a station identified by station_id.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Responses:
     *  - 200: __Successfully__ found the requested measurement.
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested measurement. *Possible causes*:   - Measurement with given measurement_id does not exist.   - Station with given station_id does not exist. 
     *
     * @param measurementId measurement_id is a __unique__ identifier for measurements.  measurement_id is an unsigned integer. 
     * @param stationId station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 
     * @return [IndexedMeasurementEntity]
     */
    @GET("stations/{station_id}/measurements/{measurement_id}")
    suspend fun getStationMeasurementById(@Path("measurement_id") measurementId: kotlin.Long, @Path("station_id") stationId: kotlin.Long): Response<IndexedMeasurementEntity>

    /**
     * Find multiple measurements of a station in a given time range or relative to a measurement.
     * ## Functionality:  Returns __multiple__ measurements of a station in a given time range or relative to a measurement.  __Important__: Parameters pairs date_begin, date_end and measurement_id, offset are mutually exclusive. Either use date_begin and date_end or measurement_id and offset when sending a request. Mixing them will throw a bad request error. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Responses:
     *  - 200: __Successfully__ found the requested measurements.
     *  - 206: __Successfully__ found part of the requested measurements.   *Note*: This response is returned when only a part of the requested measurements can be retrieved. 
     *  - 400: __Failed__ to process request. *Possible causes*:   - Malformed or missing input parameter. 
     *  - 403: __Failed__ to authorize request with the provided JWT token. *Possible causes*:   - JWT token is invalid. 
     *  - 404: __Failed__ to find the requested measurements. *Possible causes*:   - Measurement with given measurement_id does not exist.   - Station with given station_id does not exist.    
     *
     * @param stationId station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 
     * @param dateBegin date_begin is the timestamp of which the returned measurements must be chronologically bigger or equal to. date_begin follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  (optional)
     * @param dateEnd date_end is the timestamp of which the returned measurements must be chronologically smaller or equal to. date_end follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  (optional)
     * @param measurementId measurement_id is a __unique__ identifier for measurements.  measurement_id is an unsigned integer.  (optional)
     * @param offset offset is the number of measurements to be returned before or after the measurement provided in parameter measurement_id. The signed property of offset is used to decide the returned measurements. For more information see the table below: | offset value | result | |---|---| | offset &lt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __before__ measurement_id is returned.   | | offset &gt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __after__ measurement_id is returned.   | | offset &#x3D; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id is returned.   |  (optional)
     * @return [kotlin.collections.List<IndexedMeasurementEntity>]
     */
    @GET("stations/{station_id}/measurements")
    suspend fun getStationMeasurementsByQuery(@Path("station_id") stationId: kotlin.Long, @Query("date_begin") dateBegin: kotlin.Long? = null, @Query("date_end") dateEnd: kotlin.Long? = null, @Query("measurement_id") measurementId: kotlin.Long? = null, @Query("offset") offset: kotlin.Long? = null): Response<kotlin.collections.List<IndexedMeasurementEntity>>

}
