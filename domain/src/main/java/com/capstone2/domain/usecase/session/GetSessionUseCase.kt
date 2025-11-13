package com.capstone2.domain.usecase.session

import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke(): Result<List<GetSessionList>> {
        return sessionRemoteRepository.getSessionList()
    }
}