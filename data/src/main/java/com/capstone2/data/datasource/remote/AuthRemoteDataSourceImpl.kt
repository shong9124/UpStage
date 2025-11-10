package com.capstone2.data.datasource.remote

import com.capstone2.data.model.auth.LoginRequestDTO
import com.capstone2.data.model.auth.LoginResponseDTO
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.data.model.auth.SignUpResponseDTO
import com.capstone2.data.service.AuthService
import com.capstone2.domain.repository.TokenRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val service: AuthService,
    private val tokenRepository: TokenRepository,
): AuthRemoteDataSource {

    override suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO> {
        return service.signUp(body)
    }

    override suspend fun login(email: String, password: String): Response<LoginResponseDTO> {
        return service.login(LoginRequestDTO(email, password))
    }
}