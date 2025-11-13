package com.capstone2.data.repository

import com.capstone2.data.datasource.local.SessionLocalDataSource
import com.capstone2.domain.repository.SessionLocalRepository
import javax.inject.Inject

class SessionLocalRepositoryImpl @Inject constructor(
    private val sessionLocalDataSource: SessionLocalDataSource
) : SessionLocalRepository {
    override suspend fun saveSessionId(sessionId: Int, sessionStatus: String): Result<Unit> {
        return sessionLocalDataSource.saveSessionId(sessionId, sessionStatus)
    }

    override fun getSession() = sessionLocalDataSource.getSession()

    override suspend fun clearSession(): Result<Unit> {
        return sessionLocalDataSource.clearSession()
    }
}