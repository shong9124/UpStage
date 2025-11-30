package com.capstone2.domain.repository

import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.ConnectSessionResult
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.model.session.GetHistoryResult
import com.capstone2.domain.model.session.GetScoresResult
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult
import com.capstone2.domain.model.session.ai.AiAnalysisResult

interface SessionRemoteRepository {
    suspend fun createSession(modelVersion: String, title: String): Result<CreateSession>

    suspend fun getSessionList(): Result<List<GetSessionList>>

    suspend fun saveScript(sessionId: Int, body: SaveScript): Result<SaveScriptResult>

    suspend fun connectSession(id: Int, body: ConnectSession): Result<ConnectSessionResult>

    suspend fun aiAnalysis(sessionId: Int): Result<AiAnalysisResult>

    suspend fun getScores(): Result<List<GetScoresResult>>

    suspend fun getHistory(): Result<List<GetHistoryResult>>
}