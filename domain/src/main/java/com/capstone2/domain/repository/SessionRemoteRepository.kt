package com.capstone2.domain.repository

import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult

interface SessionRemoteRepository {
    suspend fun createSession(modelVersion: String, title: String): Result<CreateSession>

    suspend fun getSessionList(): Result<List<GetSessionList>>

    suspend fun saveScript(sessionId: Int, body: SaveScript): Result<SaveScriptResult>
}