package com.capstone2.data.datasource.remote

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import com.capstone2.data.model.audio.UpdateDBStateResponseDTO
import com.capstone2.data.model.audio.UploadUrlRequestDTO
import com.capstone2.data.model.audio.UploadUrlResponseDTO
import retrofit2.Response

interface AudioRemoteDataSource {
    suspend fun requestAudioFile(body: RequestAudioFileRequestDTO): Response<RequestAudioFileResponseDTO>

    suspend fun uploadUrl(body: UploadUrlRequestDTO): Response<UploadUrlResponseDTO>

    suspend fun updateDBStatus(objectPath: String): Response<UpdateDBStateResponseDTO>
}