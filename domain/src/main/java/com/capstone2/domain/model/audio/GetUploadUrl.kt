package com.capstone2.domain.model.audio

data class GetUploadUrl(
    val contentType: String,
    val filename: String,
    val sessionId: Int,
    val sizeBytes: Int,
    val userId: Int
)