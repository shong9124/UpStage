package com.capstone2.data.repository

import com.capstone2.data.datasource.remote.AuthRemoteDataSource
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signUp(email: String, password: String, name: String): Result<Boolean> {
        return try {
            val response = dataSource.signUp(SignUpRequestDTO(email, password, name))
            if(response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}