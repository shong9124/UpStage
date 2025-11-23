package com.capstone2.data.datasource.remote

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import com.capstone2.data.model.audio.UploadUrlRequestDTO
import com.capstone2.data.model.audio.UploadUrlResponseDTO
import com.capstone2.data.service.AudioService
import com.capstone2.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class AudioRemoteDataSourceImpl @Inject constructor(
    private val service: AudioService,
    private val tokenRepository: TokenRepository,
): AudioRemoteDataSource {

    private suspend fun getAccessTokenWithPrefix(): String = "Bearer ${tokenRepository.getTokens().first().accessToken}"

    override suspend fun requestAudioFile(body: RequestAudioFileRequestDTO): Response<RequestAudioFileResponseDTO> {
        return service.requestAudioFile(getAccessTokenWithPrefix(), body)
    }

    override suspend fun uploadUrl(body: UploadUrlRequestDTO): Response<UploadUrlResponseDTO> {
        return service.uploadUrl(getAccessTokenWithPrefix(), body)
    }
}