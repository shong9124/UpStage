package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.SessionRemoteDataSource
import com.capstone2.data.mapper.toDomain
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.ConnectSessionResult
import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.model.session.GetScoresResult
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult
import com.capstone2.domain.model.session.ai.AiAnalysisResult
import com.capstone2.domain.repository.SessionRemoteRepository
import com.capstone2.util.LoggerUtil
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

    override suspend fun aiAnalysis(sessionId: Int): Result<AiAnalysisResult> {
        return try {
            val response = dataSource.aiAnalysis(sessionId)
            if (response.isSuccessful) {
                val resBody = response.body()
                if (resBody != null) {
                    Result.success(resBody.let {
                        AiAnalysisResult(
                            category = it.category,
                            durationSec = it.durationSec,
                            feedbackMarkdown = it.feedbackMarkdown,
                            fillerCount = it.fillerCount,
                            fillerTimeline = it.fillerTimeline.map { filler -> filler.toDomain() },
                            fillersPerMinute = it.fillersPerMinute,
                            pronounce = it.pronounce.toDomain(),
                            pronounceCoverage = it.pronounceCoverage,
                            scoreMetrics = it.scoreMetrics.toDomain(),
                            sessionId = it.sessionId,
                            speedTimeline = it.speedTimeline.map { speed -> speed.toDomain() },
                            wer = it.wer,
                            wpm = it.wpm
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

    override suspend fun getScores(): Result<List<GetScoresResult>> {
        return try {
            val response = dataSource.getScores()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.map {
                        GetScoresResult(
                            date = it.date,
                            finalScore = it.finalScore,
                            sessionId = it.sessionId
                        )
                    })
                } else {
                    LoggerUtil.e("HTTP 200이지만 Body가 null입니다.")
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