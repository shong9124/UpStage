package com.capstone2.domain.repository

import com.capstone2.domain.model.auth.GetUserInfo

interface AuthRepository {

    suspend fun signUp(email: String, password: String, name: String): Result<Boolean>

    suspend fun login(email: String, password: String): Result<Boolean>

    suspend fun getUserInfo(): Result<GetUserInfo>
}