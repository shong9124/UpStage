package com.capstone2.domain.usecase.auth

import com.capstone2.domain.model.auth.LoginResult
import com.capstone2.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password:String): Result<LoginResult> {
        return authRepository.login(email, password)
    }
}