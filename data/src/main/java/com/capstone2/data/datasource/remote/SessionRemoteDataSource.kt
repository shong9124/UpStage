package com.capstone2.data.datasource.remote

import com.capstone2.data.model.session.ai.AiAnalysisResponseDTO
import com.capstone2.data.model.session.ConnectSessionRequestDTO
import com.capstone2.data.model.session.ConnectSessionResponseDTO
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import com.capstone2.data.model.session.GetSessionListResponseDTO
import com.capstone2.data.model.session.SaveScriptRequestDTO
import com.capstone2.data.model.session.SaveScriptResponseDTO
import retrofit2.Response

interface SessionRemoteDataSource {
    suspend fun createSession(body: CreateSessionRequestDTO): Response<CreateSessionResponseDTO>

    suspend fun getSessionList(): Response<List<GetSessionListResponseDTO>>

    suspend fun saveScript(sessionId: Int, body: SaveScriptRequestDTO): Response<SaveScriptResponseDTO>

    suspend fun connectSession(id: Int, body: ConnectSessionRequestDTO): Response<ConnectSessionResponseDTO>

    suspend fun aiAnalysis(sessionId: Int): Response<AiAnalysisResponseDTO>
}