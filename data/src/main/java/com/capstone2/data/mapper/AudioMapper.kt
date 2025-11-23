package com.capstone2.data.mapper

import com.capstone2.data.model.audio.RequestAudioFileRequestDTO
import com.capstone2.data.model.audio.RequestAudioFileResponseDTO
import com.capstone2.data.model.audio.UpdateDBStateResponseDTO
import com.capstone2.data.model.audio.UploadUrlRequestDTO
import com.capstone2.data.model.audio.UploadUrlResponseDTO
import com.capstone2.domain.model.audio.GetUploadUrl
import com.capstone2.domain.model.audio.GetUploadUrlResult
import com.capstone2.domain.model.audio.RequestAudioFile
import com.capstone2.domain.model.audio.RequestAudioFileResult
import com.capstone2.domain.model.audio.UpdateDBStatusResult

fun RequestAudioFile.toDomain(): RequestAudioFileRequestDTO {
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

fun GetUploadUrl.toDomain(): UploadUrlRequestDTO {
    return UploadUrlRequestDTO(
        contentType = this.contentType,
        filename = this.filename,
        sessionId = this.sessionId,
        sizeBytes = this.sizeBytes,
        userId = this.userId
    )
}

fun UploadUrlResponseDTO.toDomain(): GetUploadUrlResult {
    return GetUploadUrlResult(
        expiresAt = this.expiresAt,
        gcsUri = this.gcsUri,
        method = this.method,
        objectName = this.objectName,
        uploadUrl = this.uploadUrl
    )
}

fun UpdateDBStateResponseDTO.toDomain(): UpdateDBStatusResult {
    return UpdateDBStatusResult(
        channels = this.channels,
        contentType = this.contentType,
        durationMs = this.durationMs,
        gcsUri = this.gcsUri,
        id = this.id,
        objectPath = this.objectPath,
        sampleRate = this.sampleRate,
        sessionId = this.sessionId,
        sizeBytes = this.sizeBytes,
        status = this.status,
        uploaderId = this.uploaderId
    )
}