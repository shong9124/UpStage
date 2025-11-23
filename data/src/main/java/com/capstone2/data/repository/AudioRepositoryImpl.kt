package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.AudioRemoteDataSource
import com.capstone2.data.mapper.toDomain
import com.capstone2.domain.model.audio.GetUploadUrl
import com.capstone2.domain.model.audio.GetUploadUrlResult
import okhttp3.Request
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import com.capstone2.domain.model.audio.UpdateDBStatusResult
import com.capstone2.domain.repository.AudioRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
// 🚨 제거: URLEncoder는 더 이상 필요하지 않습니다.
// import java.net.URLEncoder
// import java.nio.charset.StandardCharsets
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val dataSource: AudioRemoteDataSource,
    private val okHttpClient: OkHttpClient
): AudioRepository {
    override suspend fun requestAudioFile(body: RequestAudioFile): Result<RequestAudioFileResult> {
        return try {
            val response = dataSource.requestAudioFile(body.toDomain())
            if (response.isSuccessful) {
                val resBody = response.body()
                if (resBody != null) {
                    Result.success(resBody.toDomain())
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure (HTTP ${response.code()}: ${response.message()})")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadAudioToPresignedUrl(
        url: String,
        file: File,
        contentType: String
    ): Result<Boolean> {
        return try {
            val requestBody = file.asRequestBody(contentType.toMediaType())
            val request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()

            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return Result.failure(Exception("GCS Upload Failed (HTTP ${response.code}: ${response.message})"))
                }
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUploadUrl(body: GetUploadUrl): Result<GetUploadUrlResult> {
        return try {
            val response = dataSource.uploadUrl(body.toDomain())
            if (response.isSuccessful) {
                val resBody = response.body()
                if (resBody != null) {
                    Result.success(resBody.toDomain())
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure (HTTP ${response.code()}: ${response.message()})")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDBStatus(objectPath: String): Result<UpdateDBStatusResult> {
        return try {
            // 🚨 수정: 수동 URL 인코딩을 제거하고, raw objectPath를 전달하여 Retrofit 기본 동작에 의존합니다.
            // Retrofit 설정(AudioService.kt)에서 인코딩을 제어합니다.

            val response = dataSource.updateDBStatus(objectPath)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    throw Exception("Body is null")
                }
            } else {
                // 🚨 실패 시 상세 정보 (HTTP 코드 및 에러 바디/메시지) 포함
                val errorBody = response.errorBody()?.string() ?: response.message()
                throw Exception("Request is failure (HTTP ${response.code()}: $errorBody)")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}