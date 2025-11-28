package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class SpeedTimeline(
    @SerializedName("end")
    val end: Double,
    @SerializedName("sps")
    val sps: Double,
    @SerializedName("start")
    val start: Double,
    @SerializedName("syllables")
    val syllables: Int,
    @SerializedName("t")
    val t: Double,
    @SerializedName("words")
    val words: Int,
    @SerializedName("wpm")
    val wpm: Double,
    @SerializedName("wps")
    val wps: Double
)