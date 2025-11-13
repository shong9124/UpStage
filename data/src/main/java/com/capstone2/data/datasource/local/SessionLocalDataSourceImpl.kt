package com.capstone2.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone2.domain.model.SessionPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SessionLocalDataSourceImpl @Inject constructor(
    @Named("sessionDataStore")
    private val dataStore: DataStore<Preferences>
): SessionLocalDataSource {

    private object PreferencesKeys {
        val SESSION_ID = intPreferencesKey("session_id")
        val SESSION_STATUS = stringPreferencesKey("session_status")
    }

    override suspend fun saveSessionId(sessionId: Int, sessionStatus: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.SESSION_ID] = sessionId
                preferences[PreferencesKeys.SESSION_STATUS] = sessionStatus
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getSession(): Flow<SessionPreferences> {
        return dataStore.data.map { preferences ->
            SessionPreferences(
                sessionId = preferences[PreferencesKeys.SESSION_ID] ?: 0,
                sessionStatus = preferences[PreferencesKeys.SESSION_STATUS] ?: ""
            )
        }
    }

    override suspend fun clearSession(): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}