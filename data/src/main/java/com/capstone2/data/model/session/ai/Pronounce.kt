package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class Pronounce(
    @SerializedName("coverage")
    val coverage: Double,
    @SerializedName("dels")
    val dels: Int,
    @SerializedName("examples")
    val examples: List<Example>,
    @SerializedName("ins")
    val ins: Int,
    @SerializedName("matchedWords")
    val matchedWords: Int,
    @SerializedName("subs")
    val subs: Int,
    @SerializedName("totalWords")
    val totalWords: Int,
    @SerializedName("wer")
    val wer: Double
)