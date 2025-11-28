package com.capstone2.domain.usecase.session

import com.capstone2.domain.model.session.ai.AiAnalysisResult
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class AiAnalysisUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke(sessionId: Int): Result<AiAnalysisResult> {
        return sessionRemoteRepository.aiAnalysis(sessionId)
    }
}