package com.capstone2.data.model.audio


import com.google.gson.annotations.SerializedName

data class UpdateDBStateResponseDTO(
    @SerializedName("channels")
    val channels: Int,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("durationMs")
    val durationMs: Int,
    @SerializedName("gcsUri")
    val gcsUri: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("objectPath")
    val objectPath: String,
    @SerializedName("sampleRate")
    val sampleRate: Int,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("sizeBytes")
    val sizeBytes: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("uploaderId")
    val uploaderId: Int
)