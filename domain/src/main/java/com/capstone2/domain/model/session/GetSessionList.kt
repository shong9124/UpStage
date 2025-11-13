package com.capstone2.domain.model.session

data class GetSessionList(
    val id: Int,
    val userId: Int,
    val status: String,
    val modelVersion: String,
    val title: String,
    val gcsUri: String,
    val startedAt: String,
    val completedAt: String,
    val createdAt: String,
    val updatedAt: String,
)