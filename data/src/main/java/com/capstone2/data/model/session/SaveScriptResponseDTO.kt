package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class SaveScriptResponseDTO(
    @SerializedName("content")
    val content: String,
    @SerializedName("gcsUri")
    val gcsUri: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("sha256")
    val sha256: String,
    @SerializedName("signedUrl")
    val signedUrl: String,
    @SerializedName("sizeBytes")
    val sizeBytes: Int,
    @SerializedName("version")
    val version: Int
)