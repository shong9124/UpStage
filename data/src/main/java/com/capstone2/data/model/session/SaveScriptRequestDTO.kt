package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class SaveScriptRequestDTO(
    @SerializedName("content")
    val content: String,
    @SerializedName("issueSignedUrl")
    val issueSignedUrl: Boolean,
    @SerializedName("language")
    val language: String
)