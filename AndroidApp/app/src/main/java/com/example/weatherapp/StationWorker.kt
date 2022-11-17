package com.example.weatherapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*

class StationWorker(
    private val appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters)
{

    companion object {
        const val API_KEY = "api_key"
        const val ADDRESSES = "hw_addresses"

        const val CHANNEL_ID = "WeatherStationWorker"
        const val NOTIFICATION_ID = 2

        const val CHAN_NAME = "Weather Station Worker notifications"
        const val CHAN_DESC = "Notifications for the Weather Station mobile application"
        const val TITLE = "Weather Station Worker"
        const val BODY = "Weather Station application is running in the background"

    }

    private val notificationManager: NotificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val apiKey = inputData.getString(API_KEY) ?: return Result.failure()
        val addresses = inputData.getStringArray(ADDRESSES) ?: return Result.failure()
        setForeground(createForegroundInfo())

        return try {
            StationService.startService(appContext, apiKey, addresses)
            Result.success()
        } catch (ex : Exception) {
            Result.failure()
        }
    }

    private fun createForegroundInfo() : ForegroundInfo {
        createNotificationChannel()

        val notification =
            NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setContentTitle(TITLE)
                .setSmallIcon(R.drawable.hollow_cloud)
                .setContentText(BODY)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        //requires higher api level: FOREGROUND_SERVICE_TYPE_LOCATION or FOREGROUND_SERVICE_TYPE_CAMERA
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val chan = NotificationChannel(CHANNEL_ID, CHAN_NAME, importance).apply {
                description = CHAN_DESC
            }

            notificationManager.createNotificationChannel(chan)
        }
    }

}

//we will need this if we have ongoing notifications
class TerminatingWorker(
    private val appContext: Context,
    workerParameters: WorkerParameters
) : Worker(appContext, workerParameters){
    override fun doWork(): Result {
        val notificationManager: NotificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(StationWorker.NOTIFICATION_ID)
        // closeNotification(appContext)
        return Result.success()
    }
}

