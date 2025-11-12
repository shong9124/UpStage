package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.AuthRemoteDataSource
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.domain.model.auth.GetUserInfo
import com.capstone2.domain.repository.AuthRepository
import com.capstone2.domain.repository.TokenRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthRemoteDataSource,
    private val tokenRepository: TokenRepository
) : AuthRepository {
    override suspend fun signUp(email: String, password: String, name: String): Result<Boolean> {
        return try {
            val response = dataSource.signUp(SignUpRequestDTO(email, password, name))
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val response = dataSource.login(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tokenRepository.saveTokens(body.accessToken, "")
                    Result.success(true)
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<GetUserInfo> {
        return try {
            val response = dataSource.getUserInfo()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(
                        body.let {
                            GetUserInfo(
                                id = it.id,
                                email = it.email,
                                nickname = it.nickname
                            )
                        }
                    )
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}