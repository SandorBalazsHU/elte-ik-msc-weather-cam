package com.example.weatherapp.data.hardware

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import okhttp3.logging.HttpLoggingInterceptor
import org.openapitools.client.infrastructure.Serializer
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class MeasurementsRepository (
    private val measurementsSource : MeasurementsSource = MeasurementsSource()
) {
 suspend fun measurements(address: String) : HwMeasurementEntity? =
     measurementsSource.getMeasurements(address)
}


class MeasurementsSource {
    companion object {

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        @OptIn(ExperimentalSerializationApi::class)
        operator fun invoke(baseUrl: String): HardwareApi {
            val client = OkHttpClient.Builder().apply {
                addNetworkInterceptor(loggingInterceptor)
                connectTimeout(10, TimeUnit.MINUTES)
                readTimeout(10, TimeUnit.MINUTES)
                writeTimeout(10, TimeUnit.MINUTES)
            }.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(Serializer.kotlinxSerializationJson.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(HardwareApi::class.java)
        }
    }


    suspend fun getMeasurements(endpoint: String) : HwMeasurementEntity? = withContext(Dispatchers.IO){
        val client = invoke(endpoint)
        val res = client.getMeasurements()
        //res.wait()
        if(res.isSuccessful){
            res.body()
        } else {
            Log.e("MeasurementsSource", "could not GET endpoint: ${endpoint}, error: ${res.errorBody()}")
            null
        }
    }

}