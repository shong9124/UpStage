package com.capstone2.data.model.session


import com.google.gson.annotations.SerializedName

data class ConnectSessionRequestDTO(
    @SerializedName("gcsUri")
    val gcsUri: String
)