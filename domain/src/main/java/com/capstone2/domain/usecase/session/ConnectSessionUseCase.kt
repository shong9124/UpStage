package com.capstone2.domain.usecase.session

import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.ConnectSessionResult
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class ConnectSessionUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke(id: Int, body: ConnectSession): Result<ConnectSessionResult> {
        return sessionRemoteRepository.connectSession(id, body)
    }
}