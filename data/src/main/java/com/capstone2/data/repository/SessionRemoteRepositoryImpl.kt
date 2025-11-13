package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.SessionRemoteDataSource
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class SessionRemoteRepositoryImpl @Inject constructor(
    private val dataSource: SessionRemoteDataSource
): SessionRemoteRepository {
    override suspend fun createSession(modelVersion: String, title: String): Result<CreateSession> {
        return try {
            val response = dataSource.createSession(
                CreateSessionRequestDTO(modelVersion, title)
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.let {
                        CreateSession(it.sessionId, it.status)
                    })
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}