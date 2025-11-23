package com.capstone2.data.service

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import com.capstone2.data.model.audio.UpdateDBStateResponseDTO
import com.capstone2.data.model.audio.UploadUrlRequestDTO
import com.capstone2.data.model.audio.UploadUrlResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AudioService {

    @POST("/api/audiofiles/request")
    suspend fun requestAudioFile(
        @Header("authorization") accessToken: String,
        @Body body: RequestAudioFileRequestDTO
    ): Response<RequestAudioFileResponseDTO>

    @POST("/storage/upload-url")
    suspend fun uploadUrl(
        @Header("authorization") accessToken: String,
        @Body body: UploadUrlRequestDTO
    ): Response<UploadUrlResponseDTO>

    @POST("/internal/audiofiles/mark-uploaded")
    suspend fun updateDBStatus(
        @Header("authorization") accessToken: String,
        @Query(value = "objectPath", encoded = true) objectPath: String
    ): Response<UpdateDBStateResponseDTO>
}