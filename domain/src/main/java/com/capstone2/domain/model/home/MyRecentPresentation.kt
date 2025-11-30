package com.capstone2.domain.model.home

data class MyRecentPresentation(
    val sessionId: Int,
    val title: String,
    val scriptGcsUri: String,
    val audioGcsUri: String,
    val feedbackGcsUri: String
)
