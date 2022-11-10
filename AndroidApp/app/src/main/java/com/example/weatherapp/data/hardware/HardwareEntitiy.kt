package com.example.weatherapp.data.hardware

import com.google.protobuf.Timestamp

data class HardwareEntity(
    val nickname : String,
    val ipAddress : String,
    val lastSuccess : Timestamp? = null
)