package com.example.weatherapp

import android.os.PowerManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.weatherapp.data.alarms.AlarmRepository
import com.example.weatherapp.data.hardware.HardwareEntity
import com.example.weatherapp.data.hardware.SavedHardwareRepository
import com.example.weatherapp.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val savedHardwareRepository: SavedHardwareRepository,
    private val alarmRepository: AlarmRepository,
    private val powerManager: PowerManager,
    private val stationClient: StationClient
) : ViewModel() {
    private val _hardwares : MutableStateFlow<Map<String, HardwareEntity>> = MutableStateFlow(
        emptyMap()
    )
    private val _pollingEnabled : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errMsg : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _apiKey : MutableStateFlow<String?> = MutableStateFlow(null)
    private val _cameraOn : MutableStateFlow<Boolean> = MutableStateFlow(false)

    val hardwares : StateFlow<Map<String,HardwareEntity>>
        get() = _hardwares
    val apiKey : StateFlow<String?>
        get() = _apiKey
    val pollingEnabled : StateFlow<Boolean>
        get() = _pollingEnabled
    val errMsg : StateFlow<String?>
        get() = _errMsg
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
                _errMsg.value = err.message
            }.collect { (prefs, saved) ->
                _apiKey.value = prefs.apiKey
                prefs.apiKey?.let { stationClient.setApiKey(it) }
                _hardwares.value = saved
            }
        }
    }

    fun onHardwareAdd(nickname: String, ipAddress: String){
        val newHw = HardwareEntity(nickname = nickname, ipAddress = ipAddress)
        Log.d("MINE", "ADDING HARDWARE")
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
                    cancelRequest()
                    alarmRepository.cancelRecurringAlarm()
                    false
                } else if(apiKey.value != null) {
                    //ok
                    startRequest()
                    alarmRepository.setRecurringAlarm()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun startRequest(){
        //efficiency?
//        val addresses : Array<String> =
//            hardwares.value.values.map { it.ipAddress }.toTypedArray()
//        val req: PeriodicWorkRequest =
//            PeriodicWorkRequestBuilder<StationWorker>(15, TimeUnit.MINUTES)
//                .setInputData(workDataOf(
//                    StationWorker.API_KEY to apiKey.value,
//                    StationWorker.ADDRESSES to addresses
//                ))
//                .addTag(StationWorker::class.java.name)
//                .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            StationWorker::class.java.name,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            req
//        )
    }

    private fun cancelRequest(){
//        workManager.cancelUniqueWork(StationWorker::class.java.name)
//        val req: WorkRequest =
//            OneTimeWorkRequestBuilder<TerminatingWorker>().build()
//        workManager.enqueue(
//            req
//        )
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
