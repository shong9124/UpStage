package com.capstone2.data.datasource.remote

import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import com.capstone2.data.model.session.GetSessionListResponseDTO
import retrofit2.Response

interface SessionRemoteDataSource {
    suspend fun createSession(body: CreateSessionRequestDTO): Response<CreateSessionResponseDTO>

    suspend fun getSessionList(): Response<List<GetSessionListResponseDTO>>
}