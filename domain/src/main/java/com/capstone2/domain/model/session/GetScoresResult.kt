package com.capstone2.domain.model.session

data class GetScoresResult(
    val date: String,
    val finalScore: Int,
    val sessionId: Int
)