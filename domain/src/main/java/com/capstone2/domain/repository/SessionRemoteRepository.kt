package com.capstone2.domain.repository

import com.capstone2.domain.model.session.CreateSession

interface SessionRemoteRepository {
    suspend fun createSession(modelVersion: String, title: String): Result<CreateSession>
}