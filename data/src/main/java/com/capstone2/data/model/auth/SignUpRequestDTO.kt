package com.capstone2.data.model.auth

import com.google.gson.annotations.SerializedName

data class SignUpRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("nickname")
    val nickname: String,
)