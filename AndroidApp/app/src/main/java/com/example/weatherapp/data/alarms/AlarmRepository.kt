package com.example.weatherapp.data.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("UnspecifiedImmutableFlag") //took care of it with a conditional
class AlarmRepository (val context: Context){
    val alarmFlow: Flow<Long> = callbackFlow {
        val receiver = object : android.content.BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                Log.d("CAMERA", "triggered")
                trySendBlocking(SystemClock.elapsedRealtime()).onFailure {
                    Log.e("Error", "cannot send alarm", it)
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(intentName)
        context.registerReceiver(receiver, intentFilter)
        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var pendingIntent: PendingIntent

    init {
        val intent = Intent(intentName).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
    }

    fun setRecurringAlarm(){
        Log.d("APP", "alarm set")
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 1000,
            30*1000, // Set so short for demo purposes only.
            pendingIntent
        )
    }

    fun cancelRecurringAlarm(){
        alarmManager.cancel(pendingIntent)
    }
}

private const val intentName = "com.example.WeatherApp.CAMERA_TRIGGER"


