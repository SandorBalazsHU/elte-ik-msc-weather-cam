package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.work.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {
    private val _hardwares : MutableStateFlow<Map<String, HardwareState>> = MutableStateFlow(
        emptyMap()
    )
    private val _apiKey : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _pollingEnabled : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errMsg : MutableStateFlow<String?> = MutableStateFlow(null)

    val hardwares : StateFlow<Map<String,HardwareState>>
        get() = _hardwares
    val apiKey : StateFlow<String?>
        get() = _apiKey
    val pollingEnabled : StateFlow<Boolean>
        get() = _pollingEnabled
    val errMsg : StateFlow<String?>
        get() = _errMsg

    fun onHardwareAdd(nickname: String, ipAddress: String){
        val newHw = HardwareState(nickname = nickname, ipAddress = ipAddress)
        _hardwares.update { it + (nickname to newHw) }
    }

    fun onHardwareDelete(nickname: String){
        _hardwares.update { it - nickname }
    }

    // maybe do some proper dependency injection
    fun onPollingToggle(wm: WorkManager){
        _pollingEnabled.update {
            if(it) {
                cancelRequest(wm)
                false
            } else if(apiKey.value != null) {
                //ok
                startRequest(wm)
                true
            } else {
                false
            }
        }
    }

    private fun startRequest(wm: WorkManager){
        val req: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<MeasurementWorker>(15, TimeUnit.MINUTES)
                .setInputData(workDataOf(MeasurementWorker.API_KEY to apiKey.value))
                .build()

        wm.enqueueUniquePeriodicWork(
            MeasurementWorker::class.java.name,
            ExistingPeriodicWorkPolicy.REPLACE,
            req
        )
    }

    private fun cancelRequest(wm: WorkManager){
        wm.cancelUniqueWork(MeasurementWorker::class.java.name)
        val req: WorkRequest =
            OneTimeWorkRequestBuilder<TerminatingWorker>().build()
        wm.enqueue(
            req
        )
    }

    fun onSetApiKey(key: String){
        _apiKey.value = key
    }
}

data class HardwareState(
    val nickname : String,
    val ipAddress : String,
    val active : Boolean = false
)
