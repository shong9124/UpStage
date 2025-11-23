package com.capstone2.data.datasource.remote

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import retrofit2.Response

interface AudioRemoteDataSource {
    suspend fun requestAudioFile(body: RequestAudioFileRequestDTO): Response<RequestAudioFileResponseDTO>
}