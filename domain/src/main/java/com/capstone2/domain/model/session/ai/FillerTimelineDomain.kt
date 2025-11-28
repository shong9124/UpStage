package com.capstone2.domain.model.session.ai

data class FillerTimelineDomain(
    val binEnd: Double,
    val binStart: Double,
    val fillerCount: Int,
    val fillerDurationSec: Double
)