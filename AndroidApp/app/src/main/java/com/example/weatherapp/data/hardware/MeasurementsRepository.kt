package com.example.weatherapp.data.hardware

import com.example.weatherapp.data.hardware.HwMeasurementEntity

class MeasurementsRepository (
    private val measurementsSource : MeasurementsSource = MeasurementsSource()
) {
 suspend fun measurements(address: String) : HwMeasurementEntity? =
     measurementsSource.getMeasurements(address)
}