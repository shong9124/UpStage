package com.capstone2.data.datasource.remote

import com.capstone2.data.model.session.ConnectSessionRequestDTO
import com.capstone2.data.model.session.ConnectSessionResponseDTO
import com.capstone2.data.model.session.CreateSessionRequestDTO
import com.capstone2.data.model.session.CreateSessionResponseDTO
import com.capstone2.data.model.session.GetSessionListResponseDTO
import com.capstone2.data.model.session.SaveScriptRequestDTO
import com.capstone2.data.model.session.SaveScriptResponseDTO
import com.capstone2.data.service.SessionService
import com.capstone2.domain.repository.SessionLocalRepository
import com.capstone2.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class SessionRemoteDataSourceImpl @Inject constructor(
    private val service: SessionService,
    private val tokenRepository: TokenRepository,
    private val sessionRepository: SessionLocalRepository
) : SessionRemoteDataSource {

    private suspend fun getAccessTokenWithPrefix(): String = "Bearer ${tokenRepository.getTokens().first().accessToken}"

    override suspend fun createSession(body: CreateSessionRequestDTO): Response<CreateSessionResponseDTO> {
        return service.createSession(getAccessTokenWithPrefix(), body)
    }

    override suspend fun getSessionList(): Response<List<GetSessionListResponseDTO>> {
        return service.getSessionList(getAccessTokenWithPrefix())
    }

    override suspend fun saveScript(
        sessionId: Int,
        body: SaveScriptRequestDTO
    ): Response<SaveScriptResponseDTO> {
        return service.saveScript(getAccessTokenWithPrefix(), sessionId, body)
    }

    override suspend fun connectSession(
        id: Int,
        body: ConnectSessionRequestDTO
    ): Response<ConnectSessionResponseDTO> {
        return service.connectSession(getAccessTokenWithPrefix(), id, body)
    }


}