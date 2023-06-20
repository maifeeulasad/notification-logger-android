package com.mua.roti.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


class BasicDataStore(context: Context) :
    PrefsDataStore(
        context,
        PREF_FILE_BASIC
    ),
    BasicImpl {

    override val serviceRunning: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[SERVICE_RUNNING_KEY] ?: false
        }

    override suspend fun setServiceRunningToStore(serviceRunning: Boolean) {
        dataStore.edit { preferences ->
            preferences[SERVICE_RUNNING_KEY] = serviceRunning
        }
    }

    companion object {
        private const val PREF_FILE_BASIC = "basic_preference"
        private val SERVICE_RUNNING_KEY = booleanPreferencesKey("service_running")
    }
}

@Singleton
interface BasicImpl {
    val serviceRunning: Flow<Boolean>
    suspend fun setServiceRunningToStore(serviceRunning: Boolean)
}