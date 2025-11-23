package com.capstone2.domain.model.audio

data class GetUploadUrlRequest(
    val contentType: String,
    val fileName: String,
    val sessionId: Int,
    val sizeBytes: Int
)
