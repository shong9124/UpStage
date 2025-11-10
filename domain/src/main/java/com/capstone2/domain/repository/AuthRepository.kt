package com.capstone2.domain.repository

interface AuthRepository {

    suspend fun signUp(email: String, password: String, name: String): Result<Boolean>

    suspend fun login(email: String, password: String): Result<Boolean>
}