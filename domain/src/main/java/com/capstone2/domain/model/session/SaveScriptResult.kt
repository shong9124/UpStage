package com.capstone2.domain.model.session

data class SaveScriptResult(
    val sessionId: Int,
    val gcsUri: String,
    val signedUrl: String,
    val sha256: String,
    val sizeBytes: Int,
    val version: Int,
    val language: String,
)
