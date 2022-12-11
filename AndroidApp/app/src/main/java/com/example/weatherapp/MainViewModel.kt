package com.example.weatherapp

import android.os.PowerManager
import android.util.Log
import android.view.Display.Mode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.client.ClientResult
import com.example.weatherapp.client.StationClient
import com.example.weatherapp.data.alarms.AlarmRepository
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.SavedHardwareRepository
import com.example.weatherapp.data.preferences.DEFAULT_INTERVAL
import com.example.weatherapp.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch


class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val savedHardwareRepository: SavedHardwareRepository,
    private val alarmRepository: AlarmRepository,
    private val stationClient: StationClient
) : ViewModel() {
    private val _hardwareUnits : MutableStateFlow<Map<String, HardwareEntity>> = MutableStateFlow(
        emptyMap()
    )
    private val _pollingEnabled : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _apiKey : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _pollingInterval : MutableStateFlow<Int> = MutableStateFlow(DEFAULT_INTERVAL)
    private val _cameraOn : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _messageFlow: MutableSharedFlow<ModelMessage> = MutableSharedFlow()

    val hardwareUnits : StateFlow<Map<String,HardwareEntity>>
        get() = _hardwareUnits
    val apiKey : StateFlow<String?>
        get() = _apiKey
    val pollingInterval: StateFlow<Int>
        get() = _pollingInterval
    val pollingEnabled : StateFlow<Boolean>
        get() = _pollingEnabled
    val cameraOn : StateFlow<Boolean>
        get() = _cameraOn
    val messageFlow: SharedFlow<ModelMessage>
        get() = _messageFlow

    init {
        viewModelScope.launch {
            alarmRepository.alarmFlow.collect {
                _cameraOn.value = true
            }
        }
        viewModelScope.launch {
            combine(userPreferencesRepository.userPreferencesFlow,
                    savedHardwareRepository.savedHardwareFlow) { prefs, saved ->
                prefs to saved
            }.catch { err ->
                _messageFlow.emit(ModelMessage.Error("Error while loading saved data!"))
                Log.e("ViewModel", "Saved data error", err)
            }.collect { (prefs, saved) ->
                _apiKey.value = prefs.apiKey
                _pollingInterval.value = prefs.pollingInterval
                prefs.apiKey?.let { stationClient.setApiKey(it) }
                _hardwareUnits.value = saved
            }
        }
    }

    fun onHardwareAdd(nickname: String, ipAddress: String){
        val newHw = HardwareEntity(nickname = nickname, ipAddress = ipAddress)
        viewModelScope.launch {
            savedHardwareRepository.updateHardware(newHw)
        }
    }

    fun onHardwareDelete(nickname: String){
        viewModelScope.launch {
            savedHardwareRepository.deleteHardware(nickname)
        }
    }

    fun onPollingToggle(){
        viewModelScope.launch {
            _pollingEnabled.getAndUpdate {
                if(it) {
                    alarmRepository.cancelRecurringAlarm()
                    false
                } else if(apiKey.value != null) {
                    alarmRepository.setRecurringAlarm(pollingInterval.value)
                    true
                } else {
                    false
                }
            }
        }
    }

    fun onPollingTimeSet(minutes: Int){
        viewModelScope.launch {
            userPreferencesRepository.setPollingInterval(minutes)
            _pollingEnabled.value = false
        }
    }

    fun onSetApiKey(key: String){
        viewModelScope.launch {
            userPreferencesRepository.setApiKey(key)
        }
    }

    fun onPhotoTaken(file: java.io.File){
        viewModelScope.launch {
            _messageFlow.emit(ModelMessage.Info("Uploading photo: ${file.absolutePath}"))
            val code = stationClient.addPicture(file)
            val out = code?.toString() ?: "Error"
            _messageFlow.emit(ModelMessage.Info("Upload photo result: $out"))
            _cameraOn.value = false
            finishPolling(true)
        }
    }

    fun onCameraError(ex: Throwable? = null){
        Log.e("MainViewModel", "Camera error", ex)
        viewModelScope.launch {
            _messageFlow.emit(ModelMessage.Error("Camera error!"))
        }
        _cameraOn.value = false
        finishPolling(false)
    }

    private fun finishPolling(cameraWasSuccess: Boolean){
        viewModelScope.launch {
            val (wereHwUnitsSuccess, code) = stationClient.addMeasurements(hardwareUnits.value)
            val out = code?.toString() ?: "Error"
            _messageFlow.emit(ModelMessage.Info("Measurement uploading result: $out"))
            if(wereHwUnitsSuccess){
                _messageFlow.emit(ModelMessage.Info("Polling hardware units successful!"))
            } else {
                _messageFlow.emit(ModelMessage.Error("Hardware unit error!"))
            }
            val statusCode = if(cameraWasSuccess && wereHwUnitsSuccess) { 200 } else { 500 }
            stationClient.addStatus(statusCode)
        }
    }

    companion object {
        class MainViewModelFactory(
            private val userPreferencesRepository: UserPreferencesRepository,
            private val savedHardwareRepository: SavedHardwareRepository,
            private val alarmRepository: AlarmRepository,
            private val stationClient: StationClient
        ) : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(
                        userPreferencesRepository,
                        savedHardwareRepository,
                        alarmRepository,
                        stationClient
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        alarmRepository.cancelRecurringAlarm()
    }
}

sealed class ModelMessage {
    data class Error(val msg: String) : ModelMessage()
    data class Info(val msg: String) : ModelMessage()
}

fun printModelMessage(param: ModelMessage): String =
    when(param) {
        is ModelMessage.Error ->
            "ERROR! ${param.msg}"
        is ModelMessage.Info ->
            param.msg
    }
