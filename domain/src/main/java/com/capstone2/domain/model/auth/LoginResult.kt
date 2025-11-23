package com.capstone2.domain.model.auth

data class LoginResult(
    val userId: Int,
    val email: String,
    val accessToken: String,
    val expiresInSeconds: Int
)
