package com.capstone2.data.datasource.remote

import androidx.datastore.core.DataStore
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.data.model.auth.SignUpResponseDTO
import com.capstone2.data.service.AuthService
import com.capstone2.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val service: AuthService,
    private val tokenRepository: TokenRepository,
): AuthRemoteDataSource {

    override suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO> {
        return service.signUp(body)
    }
}