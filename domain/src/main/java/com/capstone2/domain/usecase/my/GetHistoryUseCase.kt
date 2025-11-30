package com.capstone2.domain.usecase.my

import com.capstone2.domain.model.session.GetHistoryResult
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke() : Result<List<GetHistoryResult>> {
        return sessionRemoteRepository.getHistory()
    }
}