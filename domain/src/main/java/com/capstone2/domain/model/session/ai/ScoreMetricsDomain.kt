package com.capstone2.domain.model.session.ai

data class ScoreMetricsDomain(
    val baselineSpeedVarOkPct: Double,
    val baselineSpeedVarWarnPct: Double,
    val baselineWpm: Double,
    val category: String,
    val fillerDurationPct: Double,
    val fillerPerMin: Double,
    val fillerScore: Double,
    val fillerTotalCount: Int,
    val fillerTotalDurationSec: Double,
    val finalScore: Double,
    val pronAccuracyPct: Int,
    val pronScore: Double,
    val pronWerPct: Double,
    val scoreWeights: ScoreWeightsDomain,
    val speedScore: Double,
    val speedScoreMeanComponent: Double,
    val speedScoreStabilityComponent: Double,
    val userMeanDeltaPctVsBaseline: Double,
    val userSpeedVarP50Pct: Double,
    val userSpeedVarP90Pct: Double,
    val userWpm: Double
)