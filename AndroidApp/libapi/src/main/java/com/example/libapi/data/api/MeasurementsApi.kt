/**
 * Weather-Cam-API (OpenAPI 3.0)
 *
 * Weather-Cam-API is a REST API created as a part a software project at Eötvös Loránd university. The goal of the project is to turn old/unused mobile phones and an [esp8266](https://www.espressif.com/en/products/socs/esp8266) into a diy weather stations capable of recording photos, temperature, pressure and humidity data. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.example.libapi.data.api

import com.example.libapi.data.entities.ApiResponseEntity
import com.example.libapi.data.entities.IndexedMeasurementEntity
import com.example.libapi.data.entities.MeasurementEntity

import org.openapitools.client.infrastructure.*
import io.ktor.client.HttpClientConfig
import io.ktor.client.request.forms.formData
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.ParametersBuilder

    open class MeasurementsApi(
    baseUrl: String = ApiClient.BASE_URL,
    httpClientEngine: HttpClientEngine? = null,
    httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
    ) : ApiClient(baseUrl, httpClientEngine, httpClientConfig) {

        /**
        * Add new measurements to server
        * ## Functionality:  Add __multiple__ measurements a station to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 
         * @param measurementEntity **Important**:    New measurements sent to the server do **not** contain a unique identifiers station_id and measurement_id like the ones recieved by the server.   Unique identifiers are assigned by the server upon processing the measurements.  
         * @return ApiResponseEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun addMeasurements(measurementEntity: kotlin.collections.List<MeasurementEntity>): HttpResponse<ApiResponseEntity> {

            val localVariableAuthNames = listOf<String>("apiKeyAuth")

            val localVariableBody = measurementEntity

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.POST,
            "/stations/measurements",
            query = localVariableQuery,
            headers = localVariableHeaders
            )

            return jsonRequest(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
            ).wrap()
            }

        /**
        * Find the first measurement of a station.
        * ## Functionality:  Returns the __first__ measurement recieved from the weather station.    *Note*: The first measurement is defined as the first measurement of station with station_id processed by the server.   --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @return IndexedMeasurementEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getFirstStationMeasurement(stationId: java.util.UUID): HttpResponse<IndexedMeasurementEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/measurements/first".replace("{" + "station_id" + "}", "$stationId"),
            query = localVariableQuery,
            headers = localVariableHeaders
            )

            return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
            ).wrap()
            }

        /**
        * Find the latest measurement of a station.
        * ## Functionality:  Returns the __latest__ measurement recieved from the weather station.    *Note*: The latest measurement is defined as the last measurement of station with station_id processed by the server.   --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @return IndexedMeasurementEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getLatestStationMeasurement(stationId: java.util.UUID): HttpResponse<IndexedMeasurementEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/measurements/latest".replace("{" + "station_id" + "}", "$stationId"),
            query = localVariableQuery,
            headers = localVariableHeaders
            )

            return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
            ).wrap()
            }

        /**
        * Find a single measurement of a station.
        * ## Functionality:  Returns a measurement identified by measurement_id of a station identified by station_id.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
         * @param measurementId measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @return IndexedMeasurementEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getStationMeasurementById(measurementId: java.util.UUID, stationId: java.util.UUID): HttpResponse<IndexedMeasurementEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/measurements/{measurement_id}".replace("{" + "measurement_id" + "}", "$measurementId").replace("{" + "station_id" + "}", "$stationId"),
            query = localVariableQuery,
            headers = localVariableHeaders
            )

            return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
            ).wrap()
            }

        /**
        * Find multiple measurements of a station in a given time range or relative to a measurement.
        * ## Functionality:  Returns __multiple__ measurements of a station in a given time range or relative to a measurement.  __Important__: Parameters pairs date_begin, date_end and measurement_id, offset are mutually exclusive. Either use date_begin and date_end or measurement_id and offset when sending a request. Mixing them will throw a bad request error.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @param dateBegin date_begin is the timestamp of which the returned measurements must be chronologically bigger or equal to.  date_begin follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  (optional)
         * @param dateEnd date_end is the timestamp of which the returned measurements must be chronologically smaller or equal to.  date_end follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  (optional)
         * @param measurementId measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  (optional)
         * @param offset offset is the number of measurements to be returned before or after the measurement provided in parameter measurement_id.  The signed property of offset is used to decide the returned measurements. For more information see the table below:  | offset value | result | |---|---| | offset &lt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __before__ measurement_id is returned.   | | offset &gt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __after__ measurement_id is returned.   | | offset &#x3D; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id is returned.   |  (optional)
         * @return kotlin.collections.List<IndexedMeasurementEntity>
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getStationMeasurementsByQuery(stationId: java.util.UUID, dateBegin: java.math.BigDecimal?, dateEnd: java.math.BigDecimal?, measurementId: java.util.UUID?, offset: java.math.BigDecimal?): HttpResponse<kotlin.collections.List<IndexedMeasurementEntity>> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()
            dateBegin?.apply { localVariableQuery["date_begin"] = listOf("$dateBegin") }
            dateEnd?.apply { localVariableQuery["date_end"] = listOf("$dateEnd") }
            measurementId?.apply { localVariableQuery["measurement_id"] = listOf("$measurementId") }
            offset?.apply { localVariableQuery["offset"] = listOf("$offset") }

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/measurements".replace("{" + "station_id" + "}", "$stationId"),
            query = localVariableQuery,
            headers = localVariableHeaders
            )

            return request(
            localVariableConfig,
            localVariableBody,
            localVariableAuthNames
            ).wrap()
            }

        }