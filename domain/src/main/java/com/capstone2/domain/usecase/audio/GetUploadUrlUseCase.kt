package com.capstone2.domain.usecase.audio

import com.capstone2.domain.model.audio.GetUploadUrl
import com.capstone2.domain.model.audio.GetUploadUrlResult
import com.capstone2.domain.repository.AudioRepository
import javax.inject.Inject

class GetUploadUrlUseCase @Inject constructor(
    private val audioRepository: AudioRepository
) {
    suspend operator fun invoke(body: GetUploadUrl): Result<GetUploadUrlResult> {
        return audioRepository.getUploadUrl(body)
    }
}