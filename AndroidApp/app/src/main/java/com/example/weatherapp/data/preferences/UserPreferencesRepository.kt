package com.example.weatherapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TODO: we also need to save the hardware list!
data class UserPreferences(
    val apiKey: String? = null,
    //we might not need this actually
    //TODO:check consistency with actual WorkManager status
    val pollingEnabled: Boolean = false,
)

internal const val PREFERENCES_STORE_NAME = "weather_app_user_preferences"

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_STORE_NAME
)

class UserPreferencesRepository (private val dataStore: DataStore<Preferences>){
    private companion object {
        val API_KEY = stringPreferencesKey("api_key")
        val POLLING_ENABLED = booleanPreferencesKey("polling_enabled")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
            val apiKey = preferences[API_KEY]
            val pollingEnabled = preferences[POLLING_ENABLED] ?: false
            UserPreferences(apiKey, pollingEnabled)
        }

    suspend fun setApiKey(value: String) {
        dataStore.edit { preferences ->
            preferences[API_KEY] = value
        }
    }

    suspend fun updatePolling(fn: (Boolean) -> Boolean){
        dataStore.edit { preferences ->
            val newVal = fn(preferences[POLLING_ENABLED] ?: false)
            preferences[POLLING_ENABLED] = newVal
        }
    }
}