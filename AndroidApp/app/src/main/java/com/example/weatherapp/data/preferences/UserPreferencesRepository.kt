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

data class UserPreferences(
    val apiKey: String? = null,
)

internal const val PREFERENCES_STORE_NAME = "weather_app_user_preferences"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_STORE_NAME
)

class UserPreferencesRepository (context: Context){
    private val dataStore: DataStore<Preferences> = context.dataStore

    private companion object {
        val API_KEY = stringPreferencesKey("api_key")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
            val apiKey = preferences[API_KEY]
            UserPreferences(apiKey)
        }

    suspend fun setApiKey(value: String) {
        dataStore.edit { preferences ->
            preferences[API_KEY] = value
        }
    }
}