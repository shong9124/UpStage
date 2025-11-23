package com.capstone2.data.model.audio


import com.google.gson.annotations.SerializedName

data class UploadUrlRequestDTO(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("sizeBytes")
    val sizeBytes: Int,
    @SerializedName("userId")
    val userId: Int
)