package com.capstone2.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone2.domain.model.TokenPreferences
import com.capstone2.domain.model.UserIdPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class TokenLocalDataSourceImpl @Inject constructor(
    @Named("tokenDataStore")
    private val dataStore: DataStore<Preferences>
) : TokenLocalDataSource {

    private object PreferencesKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = intPreferencesKey("user_id")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
                preferences[PreferencesKeys.REFRESH_TOKEN] = refreshToken
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getTokens(): Flow<TokenPreferences> {
        return dataStore.data.map { preferences ->
            TokenPreferences(
                accessToken = preferences[PreferencesKeys.ACCESS_TOKEN] ?: "",
                refreshToken = preferences[PreferencesKeys.REFRESH_TOKEN] ?: "",
            )
        }
    }

    override suspend fun saveUserId(userId: Int): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.USER_ID] = userId
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserId(): Flow<UserIdPreferences> {
        return dataStore.data.map { preferences ->
            UserIdPreferences(
                userId = preferences[PreferencesKeys.USER_ID] ?: 0,
            )
        }
    }

    override suspend fun clearTokens(): Result<Unit> {
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