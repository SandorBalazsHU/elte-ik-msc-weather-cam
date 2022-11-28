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
import com.example.libapi.data.entities.StorageInfoEntity

import org.openapitools.client.infrastructure.*
import io.ktor.client.HttpClientConfig
import io.ktor.client.request.forms.formData
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.ParametersBuilder

    open class StationsApi(
    baseUrl: String = ApiClient.BASE_URL,
    httpClientEngine: HttpClientEngine? = null,
    httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
    ) : ApiClient(baseUrl, httpClientEngine, httpClientConfig) {

        /**
        * Returns information about the storage server for the measurements.
        * ## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 
         * @return StorageInfoEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getMeasurementStorageInfo(): HttpResponse<StorageInfoEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/measurements/storage",
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
        * Returns part of the API key for a station.
        * ## Functionality:    Returns the API key of the station identified by station_id with only the first 4 and last 4 characters visible. Every other character is replaced by an &#39;X&#39; character         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @return kotlin.String
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getPartialApiKey(stationId: java.util.UUID): HttpResponse<kotlin.String> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/api/".replace("{" + "station_id" + "}", "$stationId"),
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
        * Returns information about the storage server for the pictures.
        * ## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 
         * @return StorageInfoEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getPictureStorageInfo(): HttpResponse<StorageInfoEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/pictures/storage",
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
        * Pings the weather station.
        * ## Functionality:    Returns the status of a __single__ weather station.       ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 
         * @param stationId station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  
         * @return ApiResponseEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun getStationStatus(stationId: java.util.UUID): HttpResponse<ApiResponseEntity> {

            val localVariableAuthNames = listOf<String>("bearerAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.GET,
            "/stations/{station_id}/ping".replace("{" + "station_id" + "}", "$stationId"),
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
        * Update the status of the weather station.
        * ## Functionality:    Updates the status of the weather station.         ---   ### Prerequisites:   - This endpoint can only be used with a valid API key.   --- 
         * @param statusCode  
         * @return ApiResponseEntity
        */
            @Suppress("UNCHECKED_CAST")
        open suspend fun updateStationStatus(statusCode: java.math.BigDecimal): HttpResponse<ApiResponseEntity> {

            val localVariableAuthNames = listOf<String>("apiKeyAuth")

            val localVariableBody = 
                    io.ktor.client.utils.EmptyContent

            val localVariableQuery = mutableMapOf<String, List<String>>()

            val localVariableHeaders = mutableMapOf<String, String>()

            val localVariableConfig = RequestConfig<kotlin.Any?>(
            RequestMethod.PUT,
            "/stations/status/{status_code}".replace("{" + "status_code" + "}", "$statusCode"),
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