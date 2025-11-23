package com.capstone2.data.repository

import com.capstone2.domain.repository.TokenRepository
import com.capstone2.data.datasource.local.TokenLocalDataSource
import com.capstone2.domain.model.UserIdPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override suspend fun saveTokens(accessToken: String, refreshToken: String) : Result<Unit> {
        return tokenLocalDataSource.saveTokens(accessToken, refreshToken)
    }

    override fun getTokens() = tokenLocalDataSource.getTokens()

    override suspend fun saveUserId(userId: Int): Result<Unit> {
        return tokenLocalDataSource.saveUserId(userId)
    }

    override fun getUserId() = tokenLocalDataSource.getUserId()

    override suspend fun clearTokens(): Result<Unit> {
        return tokenLocalDataSource.clearTokens()
    }
}