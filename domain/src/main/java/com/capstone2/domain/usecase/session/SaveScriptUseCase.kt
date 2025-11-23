package com.capstone2.domain.usecase.session

import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.SaveScriptResult
import com.capstone2.domain.repository.SessionRemoteRepository
import javax.inject.Inject

class SaveScriptUseCase @Inject constructor(
    private val sessionRemoteRepository: SessionRemoteRepository
) {
    suspend operator fun invoke(sessionId: Int, body: SaveScript): Result<SaveScriptResult> {
        return sessionRemoteRepository.saveScript(sessionId, body)
    }
}