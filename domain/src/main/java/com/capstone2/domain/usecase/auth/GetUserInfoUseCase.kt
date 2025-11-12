package com.capstone2.domain.usecase.auth

import com.capstone2.domain.model.auth.GetUserInfo
import com.capstone2.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Result<GetUserInfo> {
        return authRepository.getUserInfo()
    }
}