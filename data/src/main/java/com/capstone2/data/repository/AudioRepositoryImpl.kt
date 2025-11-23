package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.AudioRemoteDataSource
import com.capstone2.data.mapper.toDomain
import okhttp3.Request
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import com.capstone2.domain.repository.AudioRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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
                throw Exception("Request is failure")
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
                    return Result.success(false)
                }
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}