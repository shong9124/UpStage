package com.capstone2.domain.usecase.session

import com.capstone2.domain.model.session.GetScoresResult
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class GetScoresUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke() : Result<List<GetScoresResult>> {
        return sessionRemoteRepository.getScores()
    }
}