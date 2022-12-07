package com.example.weatherapp

import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    private val powerManager: PowerManager,
    private val stationClient: StationClient
) : ViewModel() {
    private val _hardwareUnits : MutableStateFlow<Map<String, HardwareEntity>> = MutableStateFlow(
        emptyMap()
    )
    private val _pollingEnabled : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _apiKey : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _pollingInterval : MutableStateFlow<Int> = MutableStateFlow(DEFAULT_INTERVAL)
    private val _cameraOn : MutableStateFlow<Boolean> = MutableStateFlow(false)

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

    private val wakeLock =
        powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "weatherapp:wakelock")

    init {
        viewModelScope.launch {
            alarmRepository.alarmFlow.collect {
                wakeLock.acquire(3*60*1000L /*3 minutes*/)
                //TODO: handle result
                //stationClient.pollMeasurements(hardwares.value)
                _cameraOn.value = true
            }
        }
        viewModelScope.launch {
            combine(userPreferencesRepository.userPreferencesFlow,
                    savedHardwareRepository.savedHardwareFlow) { prefs, saved ->
                prefs to saved
            }.catch { err ->
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
        }
    }

    fun onSetApiKey(key: String){
        viewModelScope.launch {
            userPreferencesRepository.setApiKey(key)
        }
    }

    fun onPhotoTaken(file: java.io.File){
        viewModelScope.launch {
            stationClient.addPicture(file)
            wakeLock.release()
            _cameraOn.value = false
        }
    }

    fun onCameraError(ex: Throwable){
        Log.e("MainViewModel", "Camera error", ex)
        wakeLock.release()
        _cameraOn.value = false
    }

    companion object {
        class MainViewModelFactory(
            private val userPreferencesRepository: UserPreferencesRepository,
            private val savedHardwareRepository: SavedHardwareRepository,
            private val alarmRepository: AlarmRepository,
            private val powerManager: PowerManager,
            private val stationClient: StationClient
        ) : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(
                        userPreferencesRepository,
                        savedHardwareRepository,
                        alarmRepository,
                        powerManager,
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
        wakeLock.release()
    }
}
