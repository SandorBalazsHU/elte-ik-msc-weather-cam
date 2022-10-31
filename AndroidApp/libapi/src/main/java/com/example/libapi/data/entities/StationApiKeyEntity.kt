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

package com.example.libapi.data.entities


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * StationApiKey contains information about the newly added station and it's api_key.   **Important**: The api_key is only recieved once after creating a new station make sure to write it down somewhere!  
 *
 * @param stationId station_id is a __unique__ identifier for stations.   station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
 * @param apiKey api_key gives access to upload measurements to the server.   
 */
@Serializable
data class StationApiKeyEntity (

    /* station_id is a __unique__ identifier for stations.   station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  */
    @Contextual @SerialName(value = "station_id")
    val stationId: java.util.UUID? = null,

    /* api_key gives access to upload measurements to the server.    */
    @Contextual @SerialName(value = "api_key")
    val apiKey: java.util.UUID? = null

)
