package com.capstone2.data.datasource.local

import com.capstone2.domain.model.TokenPreferences
import com.capstone2.domain.model.UserIdPreferences
import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {
    suspend fun saveTokens(accessToken: String, refreshToken: String): Result<Unit>

    fun getTokens(): Flow<TokenPreferences>

    suspend fun saveUserId(userId: Int): Result<Unit>

    fun getUserId(): Flow<UserIdPreferences>

    suspend fun clearTokens(): Result<Unit>
}