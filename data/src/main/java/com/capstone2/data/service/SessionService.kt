package com.capstone2.data.service

import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import com.capstone2.data.model.session.GetSessionListResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
}