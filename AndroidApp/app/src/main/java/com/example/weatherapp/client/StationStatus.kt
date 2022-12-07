package com.example.weatherapp.client

import java.util.EnumSet

enum class StationStatus(val value: Int) {
    cameraError(1),
    hardwareError(2),
    internalError(4),
}

fun EnumSet<StationStatus>.toInt(set: EnumSet<StationStatus>): Int {
    var code = 0
    for (element in set){
        code += element.value
    }
    return code
}