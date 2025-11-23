package com.capstone2.domain.repository

import com.capstone2.domain.model.audio.GetUploadUrl
import com.capstone2.domain.model.audio.GetUploadUrlResult
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import java.io.File

interface AudioRepository {
    suspend fun requestAudioFile(body: RequestAudioFile): Result<RequestAudioFileResult>

    suspend fun uploadAudioToPresignedUrl(
        url: String,
        file: File,
        contentType: String
    ): Result<Boolean>

    suspend fun getUploadUrl(
        body: GetUploadUrl
    ): Result<GetUploadUrlResult>
}