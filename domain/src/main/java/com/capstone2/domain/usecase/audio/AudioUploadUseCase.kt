package com.capstone2.domain.usecase.audio

import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import com.capstone2.domain.repository.AudioRepository
import java.io.File
import javax.inject.Inject

class AudioUploadUseCase @Inject constructor(
    private val audioRepository: AudioRepository
) {

    /**
     * 1️⃣ 서버에 Presigned URL 요청
     */
    suspend fun requestAudioFile(request: RequestAudioFile): Result<RequestAudioFileResult> {
        return audioRepository.requestAudioFile(request)
    }

    /**
     * 2️⃣ Presigned URL로 실제 파일 업로드
     */
    suspend fun uploadAudioToPresignedUrl(
        url: String,
        file: File,
        contentType: String
    ): Result<Boolean> {
        return audioRepository.uploadAudioToPresignedUrl(url, file, contentType)
    }
}
