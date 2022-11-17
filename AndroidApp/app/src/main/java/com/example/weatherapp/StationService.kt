package com.example.weatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.libapi.data.api.MeasurementsApi
import com.example.libapi.data.entities.MeasurementEntity
import com.example.weatherapp.data.hardware.HwMeasurementEntity
import com.example.weatherapp.data.hardware.MeasurementsRepository
import io.ktor.client.engine.android.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

/*
A oneshot service used to get the measurements from the hardware units, take a picture, and
send it to the API.
*/
class StationService : LifecycleService() {
    //private val measurementsRepository by lazy {
    //    MeasurementsRepository()
    //}
    private val webApi by lazy {
        MeasurementsApi(
            baseUrl = "http://mock.weather.s-b-x.com",
            httpClientEngine = Android.create {
                connectTimeout = 10_000
                socketTimeout = 10_000
                // proxy?
                // auth?
            }
        )
    }

    companion object {
        const val API_KEY = "api_key"
        const val ADDRESSES = "hw_addresses"

        const val CHANNEL_ID = "WeatherStationService"
        const val NOTIFICATION_ID = 1
        const val CHAN_NAME = "Weather Station Service notifications"
        const val CHAN_DESC = "Notifications for the Weather Station mobile application"
        const val TITLE = "Weather Station Service"
        const val BODY = "Collecting and sending data..."

        fun startService(
            context: Context,
            apiKey: String,
            addresses: Array<String?>
        ) {
            val startIntent = Intent(context, StationService::class.java)
            startIntent.putExtra(API_KEY, apiKey)
            startIntent.putExtra(ADDRESSES, addresses)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, StationService::class.java)
            context.stopService(stopIntent)
        }

    }

    private val notificationBuilder =
        NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle(TITLE)
        .setSmallIcon(R.drawable.hollow_cloud)
        .setContentText(BODY)
       // .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        createNotificationChannel()

        val addresses = intent?.getStringArrayExtra(ADDRESSES)?.filterNotNull()!!
        Log.d("StationService", addresses.toString())
        val apiKey = intent.getStringExtra(API_KEY)!!
       // webApi.setApiKey(apiKey)
//        val notificationIntent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0, notificationIntent, 0
//        )

        val notification = notificationBuilder.build()
        startForeground(NOTIFICATION_ID, notification)

        //does this finish in time?
        lifecycleScope.launch(Dispatchers.IO) {
            val measurements = addresses.map { getMeasurement(it) }
            try {
               // webApi.addMeasurements(measurements)
                updateNotification(measurements)
            } catch (ex: Exception){
                errorNotification(ex.message ?: "Error, no error message could be found")
            }
        }
        stopForeground(true)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private fun errorNotification(err: String){
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val dateText = sdf.format(currentDate)
        val text = "Got error at: $dateText\n"
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(err))
                .build())
    }

    private fun updateNotification(measurements: List<MeasurementEntity>){
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val dateText = sdf.format(currentDate)
        val text = "Last data sent at: $dateText\n  number of hws: ${measurements.size}\n"
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.setContentText(text).build())
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chan = NotificationChannel(CHANNEL_ID, CHAN_NAME, importance).apply {
                description = CHAN_DESC
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(chan)
        }
    }

    private suspend fun getMeasurement(address: String) : MeasurementEntity {
      //  val hwEnt = measurementsRepository.measurements(address)!!
      //  return translateMeasurement(hwEnt)
        return MeasurementEntity(20f,20f,20f)
    }

    //TODO: getPicture

    private fun translateMeasurement(hwEnt : HwMeasurementEntity) : MeasurementEntity =
        MeasurementEntity(
            temperature = hwEnt.temp,
            pressure = hwEnt.pressure,
            humidity = hwEnt.humidity
        )
}