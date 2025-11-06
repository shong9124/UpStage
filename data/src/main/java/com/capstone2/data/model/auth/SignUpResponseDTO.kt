package com.capstone2.data.model.auth


import com.google.gson.annotations.SerializedName

data class SignUpResponseDTO(
    @SerializedName("message")
    val message: String,
    @SerializedName("userId")
    val userId: Int
)