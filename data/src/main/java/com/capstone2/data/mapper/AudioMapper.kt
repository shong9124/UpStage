package com.capstone2.data.mapper

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult

fun RequestAudioFile.toDomain() : RequestAudioFileRequestDTO {
    return RequestAudioFileRequestDTO(
        contentType = this.contentType,
        gcsUri = this.gcsUri,
        objectPath = this.objectPath,
        sessionId = this.sessionId,
        sizeBytes = this.sizeBytes,
        uploaderId = this.uploaderId
    )
}

fun RequestAudioFileResponseDTO.toDomain(): RequestAudioFileResult {
    return RequestAudioFileResult(
        expiresAt = this.expiresAt,
        gcsUri = this.gcsUri,
        method = this.method,
        objectName = this.objectName,
        uploadUrl = this.uploadUrl
    )
}