package com.example.weatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.core.content.ContextCompat.getSystemService
import com.example.libapi.data.entities.MeasurementEntity
import java.text.SimpleDateFormat
import java.util.*


class WeatherNotification(private val context: Context) {
    private val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(TITLE)
        .setSmallIcon(R.drawable.hollow_cloud)
        .setContentText(BODY)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    companion object {
        const val CHANNEL_ID = "WeatherStationChannel"
        const val NOTIFICATION_ID = 0
        const val CHAN_NAME = "Weather Station notifications"
        const val CHAN_DESC = "Notifications for the Weather Station mobile application"
        const val TITLE = "Weather Station Task"
        const val BODY = "Weather Station application is running in the background"

        fun closeNotification(appContext: Context){
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NOTIFICATION_ID)
        }
    }

    init {
        showNotification()
    }

    fun errorNotification(err: String){
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val dateText = sdf.format(currentDate)
        val text = "Got error at: $dateText\n"
        val notificationManager = from(context)
        notificationManager.notify(NOTIFICATION_ID,
            builder.setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(err))
                .build())
    }

    fun updateNotification(measurementEntity: MeasurementEntity){
        val currentDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val dateText = sdf.format(currentDate)
        val text = "Last data sent at: $dateText\n  temp: ${measurementEntity.temperature}\n"
        val notificationManager = from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.setContentText(text).build())
    }

    private fun showNotification(){
        createNotificationChannel()
        val notificationManager = from(context)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    // It's safe to call this repeatedly because creating an existing notification
    // channel performs no operation.
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chan = NotificationChannel(CHANNEL_ID, CHAN_NAME, importance).apply {
                description = CHAN_DESC
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(chan)
        }
    }
}
