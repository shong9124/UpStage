package com.capstone2.data.service

import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.data.model.auth.SignUpResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/users/register")
    suspend fun signUp(
        @Body body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>


}