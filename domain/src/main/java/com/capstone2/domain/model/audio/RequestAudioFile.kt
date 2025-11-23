package com.capstone2.domain.model.audio

data class RequestAudioFile(
    val sessionId: Int,
    val uploaderId : Int,
    val gcsUri: String,
    val objectPath: String,
    val contentType: String,
    val sizeBytes: Int
)
