package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class GetSessionListResponseDTO(
    @SerializedName("completedAt")
    val completedAt: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("gcsUri")
    val gcsUri: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modelVersion")
    val modelVersion: String,
    @SerializedName("startedAt")
    val startedAt: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)