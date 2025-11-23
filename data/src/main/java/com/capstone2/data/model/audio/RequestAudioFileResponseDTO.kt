package com.capstone2.data.model.audio


import com.google.gson.annotations.SerializedName

data class RequestAudioFileResponseDTO(
    @SerializedName("expiresAt")
    val expiresAt: String,
    @SerializedName("gcsUri")
    val gcsUri: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("objectName")
    val objectName: String,
    @SerializedName("uploadUrl")
    val uploadUrl: String
)