package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class FillerTimeline(
    @SerializedName("binEnd")
    val binEnd: Double,
    @SerializedName("binStart")
    val binStart: Double,
    @SerializedName("fillerCount")
    val fillerCount: Int,
    @SerializedName("fillerDurationSec")
    val fillerDurationSec: Double
)