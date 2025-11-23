package com.capstone2.data.model.audio


import com.google.gson.annotations.SerializedName

data class RequestAudioFileRequestDTO(
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("gcsUri")
    val gcsUri: String,
    @SerializedName("objectPath")
    val objectPath: String,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("sizeBytes")
    val sizeBytes: Int,
    @SerializedName("uploaderId")
    val uploaderId: Int
)