package com.capstone2.domain.usecase.audio

import com.capstone2.domain.model.audio.UpdateDBStatusResult
import com.capstone2.domain.repository.AudioRepository
import javax.inject.Inject

class UpdateDBStatusUseCase @Inject constructor(
    private val audioRepository: AudioRepository
) {
    suspend operator fun invoke(objectPath: String): Result<UpdateDBStatusResult> {
        return audioRepository.updateDBStatus(objectPath)
    }
}