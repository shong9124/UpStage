package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class ScoreMetrics(
    @SerializedName("baselineSpeedVarOkPct")
    val baselineSpeedVarOkPct: Double,
    @SerializedName("baselineSpeedVarWarnPct")
    val baselineSpeedVarWarnPct: Double,
    @SerializedName("baselineWpm")
    val baselineWpm: Double,
    @SerializedName("category")
    val category: String,
    @SerializedName("fillerDurationPct")
    val fillerDurationPct: Double,
    @SerializedName("fillerPerMin")
    val fillerPerMin: Double,
    @SerializedName("fillerScore")
    val fillerScore: Double,
    @SerializedName("fillerTotalCount")
    val fillerTotalCount: Int,
    @SerializedName("fillerTotalDurationSec")
    val fillerTotalDurationSec: Double,
    @SerializedName("finalScore")
    val finalScore: Double,
    @SerializedName("pronAccuracyPct")
    val pronAccuracyPct: Int,
    @SerializedName("pronScore")
    val pronScore: Double,
    @SerializedName("pronWerPct")
    val pronWerPct: Double,
    @SerializedName("scoreWeights")
    val scoreWeights: ScoreWeights,
    @SerializedName("speedScore")
    val speedScore: Double,
    @SerializedName("speedScoreMeanComponent")
    val speedScoreMeanComponent: Double,
    @SerializedName("speedScoreStabilityComponent")
    val speedScoreStabilityComponent: Double,
    @SerializedName("userMeanDeltaPctVsBaseline")
    val userMeanDeltaPctVsBaseline: Double,
    @SerializedName("userSpeedVarP50Pct")
    val userSpeedVarP50Pct: Double,
    @SerializedName("userSpeedVarP90Pct")
    val userSpeedVarP90Pct: Double,
    @SerializedName("userWpm")
    val userWpm: Double
)