package com.capstone2.data.service

import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SessionService {

    @POST("/sessions")
    suspend fun createSession(
        @Header("Authorization") accessToken: String,
        @Body body: CreateSessionRequestDTO
    ): Response<CreateSessionResponseDTO>
}