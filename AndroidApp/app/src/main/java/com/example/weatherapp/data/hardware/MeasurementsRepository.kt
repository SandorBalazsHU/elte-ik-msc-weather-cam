package com.example.weatherapp.data.hardware

import com.example.libapi.data.entities.MeasurementEntity

class MeasurementsRepository (
    private val measurementsSource : MeasurementsSource = MeasurementsSource()
) {
 suspend fun measurements() : MeasurementEntity = measurementsSource.getMeasurements()
}