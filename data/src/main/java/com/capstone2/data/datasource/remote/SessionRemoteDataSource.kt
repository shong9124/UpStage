package com.capstone2.data.datasource.remote

import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import retrofit2.Response

interface SessionRemoteDataSource {
    suspend fun createSession(body: CreateSessionRequestDTO): Response<CreateSessionResponseDTO>
}