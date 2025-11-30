package com.capstone2.domain.model.session

data class GetHistoryResult(
    val audioGcsUri: String,
    val createdDate: String,
    val feedbackGcsUri: String,
    val scriptGcsUri: String,
    val sessionId: Int,
    val title: String
)