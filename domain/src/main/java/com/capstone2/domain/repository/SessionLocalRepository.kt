package com.capstone2.domain.repository

import com.capstone2.domain.model.SessionPreferences
import kotlinx.coroutines.flow.Flow

interface SessionLocalRepository {
    suspend fun saveSessionId(sessionId: Int, sessionStatus: String): Result<Unit>

    fun getSession(): Flow<SessionPreferences>

    suspend fun clearSession(): Result<Unit>
}