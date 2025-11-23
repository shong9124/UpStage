package com.capstone2.data.service

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AudioService {

    @POST("/api/audiofiles/request")
    suspend fun requestAudioFile(
        @Header("authorization") accessToken: String,
        @Body body: RequestAudioFileRequestDTO
    ): Response<RequestAudioFileResponseDTO>
}