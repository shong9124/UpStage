package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class CreateSessionResponseDTO(
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("status")
    val status: String
)