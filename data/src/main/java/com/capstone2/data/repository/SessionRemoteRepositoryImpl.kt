package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.SessionRemoteDataSource
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.model.session.GetSessionList
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

    override suspend fun getSessionList(): Result<List<GetSessionList>> {
        return try {
            val response = dataSource.getSessionList()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.map {
                        GetSessionList(
                            id = it.id,
                            userId = it.userId,
                            status = it.status,
                            modelVersion = it.modelVersion,
                            title = it.title,
                            gcsUri = it.gcsUri,
                            startedAt = it.startedAt,
                            completedAt = it.completedAt,
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt
                        )
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