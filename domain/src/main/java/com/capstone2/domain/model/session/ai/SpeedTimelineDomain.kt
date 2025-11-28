package com.capstone2.domain.model.session.ai

data class SpeedTimelineDomain(
    val end: Double,
    val sps: Double,
    val start: Double,
    val syllables: Int,
    val t: Double,
    val words: Int,
    val wpm: Double,
    val wps: Double
)