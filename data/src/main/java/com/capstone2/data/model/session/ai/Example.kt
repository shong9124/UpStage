package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class Example(
    @SerializedName("asr")
    val asr: String,
    @SerializedName("next2")
    val next2: String,
    @SerializedName("prev2")
    val prev2: String,
    @SerializedName("target")
    val target: String
)