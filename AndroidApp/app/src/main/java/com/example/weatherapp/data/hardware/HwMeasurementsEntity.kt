package com.example.weatherapp.data.hardware

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Measurement contains the data recorded by the sensors.
 *
 * @param temp temperature in celsius.
 * @param pressure pressure in millibars.
 * @param humidity humidity in percentage.
 */
@Serializable
data class HwMeasurementEntity (

    /* temperature in celsius. */
    @SerialName(value = "temp")
    val temp: kotlin.Float,

    /* pressure in millibars. */
    @SerialName(value = "pressure")
    val pressure: kotlin.Float,

    /* humidity in percentage. */
    @SerialName(value = "humidity")
    val humidity: kotlin.Float,

)