package com.capstone2.domain.model.session

data class SaveScript(
    val content: String,
    val language: String,
    val issueSignedUrl: Boolean = true
)
