package com.example.weatherapp

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.hardware.MeasurementsRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class MainViewModel : ViewModel() {
    private val _hardwares : MutableStateFlow<Map<String, HardwareState>> = MutableStateFlow(
        emptyMap()
    )
    private val _apiKey : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _pollingEnabled : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errMsg : MutableStateFlow<String?> = MutableStateFlow(null)

    private val measurementsRepository by lazy {
        MeasurementsRepository()
    }

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

    fun onPollingToggle(){
        // set true if it was false and we have a key
        // set false otherwise
        // we should eventually do an error message if no api key was provided
        _pollingEnabled.update {
            !it && apiKey.value != null
        }
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

// 60 secs is the default poll interval
const val pollInterval = 60

