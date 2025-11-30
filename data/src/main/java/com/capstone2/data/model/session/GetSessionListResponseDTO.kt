package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class GetSessionListResponseDTO(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modelVersion")
    val modelVersion: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)