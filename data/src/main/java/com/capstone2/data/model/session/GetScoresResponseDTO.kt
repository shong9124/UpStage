package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class GetScoresResponseDTO(
    @SerializedName("date")
    val date: String,
    @SerializedName("finalScore")
    val finalScore: Double,
    @SerializedName("sessionId")
    val sessionId: Int
)