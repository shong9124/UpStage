package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class GetHistoryResponseDTO(
    @SerializedName("audioGcsUri")
    val audioGcsUri: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("feedbackGcsUri")
    val feedbackGcsUri: String,
    @SerializedName("scriptGcsUri")
    val scriptGcsUri: String,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("title")
    val title: String
)