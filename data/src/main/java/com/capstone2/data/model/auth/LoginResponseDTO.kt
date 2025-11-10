package com.capstone2.data.model.auth


import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("expiresInSeconds")
    val expiresInSeconds: Int,
)