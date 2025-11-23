package com.capstone2.domain.model.audio

data class RequestAudioFileResult(
    val expiresAt: String,
    val gcsUri: String,
    val method: String,
    val objectName: String,
    val uploadUrl: String
)