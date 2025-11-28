package com.capstone2.domain.model.session.ai

data class PronounceDomain(
    val coverage: Double,
    val dels: Int,
    val examples: List<ExampleDomain>,
    val ins: Int,
    val matchedWords: Int,
    val subs: Int,
    val totalWords: Int,
    val wer: Double
)