package com.capstone2.data.service

import com.capstone2.data.model.session.ai.AiAnalysisResponseDTO
import com.capstone2.data.model.session.ConnectSessionRequestDTO
import com.capstone2.data.model.session.ConnectSessionResponseDTO
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import com.capstone2.data.model.session.GetScoresResponseDTO
import com.capstone2.data.model.session.GetSessionListResponseDTO
import com.capstone2.data.model.session.SaveScriptRequestDTO
import com.capstone2.data.model.session.SaveScriptResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SessionService {

    @POST("/sessions")
    suspend fun createSession(
        @Header("Authorization") accessToken: String,
        @Body body: CreateSessionRequestDTO
    ): Response<CreateSessionResponseDTO>

    @GET("/sessions")
    suspend fun getSessionList(
        @Header("Authorization") accessToken: String
    ): Response<List<GetSessionListResponseDTO>>

    @POST("/sessions/{sessionId}/script")
    suspend fun saveScript(
        @Header("Authorization") accessToken: String,
        @Path("sessionId") sessionId: Int,
        @Body body: SaveScriptRequestDTO
    ): Response<SaveScriptResponseDTO>

    @POST("/sessions/{id}/audio")
    suspend fun connectSession(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int,
        @Body body: ConnectSessionRequestDTO
    ): Response<ConnectSessionResponseDTO>

    @POST("/sessions/{sessionId}/analysis")
    suspend fun aiAnalysis(
        @Header("Authorization") accessToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<AiAnalysisResponseDTO>

    @GET("/sessions/me/scores")
    suspend fun getScores(
        @Header("Authorization") accessToken: String
    ): Response<List<GetScoresResponseDTO>>
}