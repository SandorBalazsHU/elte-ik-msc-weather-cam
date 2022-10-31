package com.example.weatherapp.data.hardware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Measurement contains the data recorded by the sensors.
 *
 * @param temperature temperature in celsius.
 * @param pressure pressure in millibars.
 * @param humidity humidity in percentage.
 */
@Serializable
data class HwMeasurementEntity (

    /* temperature in celsius. */
    @SerialName(value = "temperature")
    val temperature: kotlin.Float? = null,

    /* pressure in millibars. */
    @SerialName(value = "pressure")
    val pressure: kotlin.Float? = null,

    /* humidity in percentage. */
    @SerialName(value = "humidity")
    val humidity: kotlin.Float? = null,

    @SerialName(value = "time")
    val time: kotlin.String? = null,

    @SerialName(value = "date")
    val date: kotlin.String? = null
)