package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class AiAnalysisResponseDTO(
    @SerializedName("category")
    val category: String,
    @SerializedName("durationSec")
    val durationSec: Double,
    @SerializedName("feedbackMarkdown")
    val feedbackMarkdown: String,
    @SerializedName("fillerCount")
    val fillerCount: Int,
    @SerializedName("fillerTimeline")
    val fillerTimeline: List<FillerTimeline>,
    @SerializedName("fillersPerMinute")
    val fillersPerMinute: Double,
    @SerializedName("pronounce")
    val pronounce: Pronounce,
    @SerializedName("pronounceCoverage")
    val pronounceCoverage: Double,
    @SerializedName("scoreMetrics")
    val scoreMetrics: ScoreMetrics,
    @SerializedName("sessionId")
    val sessionId: Int,
    @SerializedName("speedTimeline")
    val speedTimeline: List<SpeedTimeline>,
    @SerializedName("wer")
    val wer: Double,
    @SerializedName("wpm")
    val wpm: Double
)