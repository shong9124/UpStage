package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.SessionRemoteDataSource
import com.capstone2.data.mapper.toDomain
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.ConnectSessionResult
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult
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

    override suspend fun saveScript(sessionId: Int, body: SaveScript): Result<SaveScriptResult> {
        return try {
            val response = dataSource.saveScript(sessionId, body.toDomain())
            if (response.isSuccessful) {
                val resBody = response.body()
                if (resBody != null) {
                    Result.success(resBody.let {
                        SaveScriptResult(
                            sessionId = it.sessionId,
                            gcsUri = it.gcsUri,
                            signedUrl = it.signedUrl,
                            sha256 = it.sha256,
                            sizeBytes = it.sizeBytes,
                            version = it.version,
                            language = it.language,
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

    override suspend fun connectSession(id: Int, body: ConnectSession): Result<ConnectSessionResult> {
        return try {
            val response = dataSource.connectSession(id, body.toDomain())
            if (response.isSuccessful) {
                val resBody = response.body()
                if (resBody != null) {
                    Result.success(resBody.let {
                        ConnectSessionResult(
                            sessionId = it.sessionId,
                            status = it.status
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