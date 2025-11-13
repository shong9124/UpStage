package com.capstone2.data.datasource.local

import com.capstone2.domain.model.SessionPreferences
import kotlinx.coroutines.flow.Flow

interface SessionLocalDataSource {
    suspend fun saveSessionId(sessionId: Int, sessionStatus: String): Result<Unit>

    fun getSession(): Flow<SessionPreferences>

    suspend fun clearSession(): Result<Unit>

}