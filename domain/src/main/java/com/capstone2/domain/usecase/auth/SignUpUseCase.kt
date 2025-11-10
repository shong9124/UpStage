package com.capstone2.domain.usecase.auth

import com.capstone2.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<Boolean> {
        return authRepository.signUp(email, password, name)
    }
}