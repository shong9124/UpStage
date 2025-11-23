package com.capstone2.domain.model.audio

data class UpdateDBStatusResult(
    val channels: Int,
    val contentType: String,
    val durationMs: Int,
    val gcsUri: String,
    val id: Int,
    val objectPath: String,
    val sampleRate: Int,
    val sessionId: Int,
    val sizeBytes: Int,
    val status: String,
    val uploaderId: Int
)