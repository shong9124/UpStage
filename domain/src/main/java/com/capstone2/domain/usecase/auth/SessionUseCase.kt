package com.capstone2.domain.usecase.auth

import com.capstone2.domain.model.session.CreateSession
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class SessionUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke(modelVersion: String, title: String): Result<CreateSession>{
        return sessionRemoteRepository.createSession(modelVersion, title)
    }
}