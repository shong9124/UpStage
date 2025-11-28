package com.capstone2.domain.model.session.ai

data class AiAnalysisResult(
    val category: String,
    val durationSec: Double,
    val feedbackMarkdown: String,
    val fillerCount: Int,
    val fillerTimeline: List<FillerTimelineDomain>,
    val fillersPerMinute: Double,
    val pronounce: PronounceDomain,
    val pronounceCoverage: Double,
    val scoreMetrics: ScoreMetricsDomain,
    val sessionId: Int,
    val speedTimeline: List<SpeedTimelineDomain>,
    val wer: Double,
    val wpm: Double
)
