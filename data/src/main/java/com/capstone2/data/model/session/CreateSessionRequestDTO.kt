package com.capstone2.data.model.session

import com.google.gson.annotations.SerializedName

data class CreateSessionRequestDTO(
    @SerializedName("modelVersion")
    val modelVersion: String,
    @SerializedName("title")
    val title: String
)