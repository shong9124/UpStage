package com.capstone2.domain.model.audio

data class GetUploadUrlResult(
    val expiresAt: String,
    val gcsUri: String,
    val method: String,
    val objectName: String,
    val uploadUrl: String
)