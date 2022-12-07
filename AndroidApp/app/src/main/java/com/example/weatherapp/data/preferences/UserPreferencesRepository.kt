package com.example.weatherapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(
    val apiKey: String? = null,
    val pollingInterval: Int = DEFAULT_INTERVAL,
)

internal const val PREFERENCES_STORE_NAME = "weather_app_user_preferences"
const val DEFAULT_INTERVAL = 15


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_STORE_NAME,
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { emptyPreferences() }
    )
)

class UserPreferencesRepository (context: Context){
    private val dataStore: DataStore<Preferences> = context.dataStore

    private companion object {
        val API_KEY = stringPreferencesKey("api_key")
        val POLLING_INTERVAL = intPreferencesKey("polling_interval")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val apiKey = preferences[API_KEY]
            val pollingInterval = preferences[POLLING_INTERVAL]
            UserPreferences(apiKey, pollingInterval ?: DEFAULT_INTERVAL)
        }

    suspend fun setApiKey(value: String) {
        dataStore.edit { preferences ->
            preferences[API_KEY] = value
        }
    }

    suspend fun setPollingInterval(value: Int) {
        dataStore.edit { preferences ->
            preferences[POLLING_INTERVAL] = value
        }
    }

}