package com.capstone2.data.model.auth


import com.google.gson.annotations.SerializedName

data class GetUserInfoResponseDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    val nickname: String
)