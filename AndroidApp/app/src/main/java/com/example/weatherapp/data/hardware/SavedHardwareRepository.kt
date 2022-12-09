package com.example.weatherapp.data.hardware

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val STORE_NAME = "weatherapp_hardware.pb"

private val Context.hardwareDataStore: DataStore<HardwareMap> by dataStore(
    fileName = STORE_NAME,
    serializer = HardwareSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { HardwareSerializer.defaultValue }
    )
)



class SavedHardwareRepository(context: Context){
    private val hardwareDataStore: DataStore<HardwareMap> = context.hardwareDataStore
    val savedHardwareFlow : Flow<Map<String, HardwareEntity>> = hardwareDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(HardwareSerializer.defaultValue)
            } else {
                throw exception
            }
        }
        .map {
            fromSavedMap(it)
        }

    suspend fun updateHardware(ent: HardwareEntity){
        hardwareDataStore.updateData { current ->
            current.toBuilder().putHws(ent.nickname, toSaved(ent)).build()
        }
    }

    suspend fun deleteHardware(name: String){
        hardwareDataStore.updateData { current ->
            current.toBuilder().removeHws(name).build()
        }
    }
}