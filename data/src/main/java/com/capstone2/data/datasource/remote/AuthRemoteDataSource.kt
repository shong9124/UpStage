package com.capstone2.data.datasource.remote

import com.capstone2.data.model.auth.LoginResponseDTO
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.data.model.auth.SignUpResponseDTO
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO>

    suspend fun login(email: String, password: String): Response<LoginResponseDTO>
}