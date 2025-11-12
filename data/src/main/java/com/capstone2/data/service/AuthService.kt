package com.capstone2.data.service

import com.capstone2.data.model.auth.GetUserInfoResponseDTO
import com.capstone2.data.model.auth.LoginRequestDTO
import com.capstone2.data.model.auth.LoginResponseDTO
import com.capstone2.data.model.auth.SignUpRequestDTO
import com.capstone2.data.model.auth.SignUpResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/users/register")
    suspend fun signUp(
        @Body body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>

    @POST("/users/login")
    suspend fun login(
        @Body body: LoginRequestDTO
    ): Response<LoginResponseDTO>

    @GET("/users/me")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String
    ): Response<GetUserInfoResponseDTO>
}