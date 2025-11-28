package com.capstone2.data.mapper

import com.capstone2.data.model.session.ConnectSessionRequestDTO
import com.capstone2.data.model.session.SaveScriptRequestDTO
import com.capstone2.data.model.session.ai.Example
import com.capstone2.data.model.session.ai.FillerTimeline
import com.capstone2.data.model.session.ai.Pronounce
import com.capstone2.data.model.session.ai.ScoreMetrics
import com.capstone2.data.model.session.ai.ScoreWeights
import com.capstone2.data.model.session.ai.SpeedTimeline
import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.SaveScript
import com.capstone2.domain.model.session.ai.ExampleDomain
import com.capstone2.domain.model.session.ai.FillerTimelineDomain
import com.capstone2.domain.model.session.ai.PronounceDomain
import com.capstone2.domain.model.session.ai.ScoreMetricsDomain
import com.capstone2.domain.model.session.ai.ScoreWeightsDomain
import com.capstone2.domain.model.session.ai.SpeedTimelineDomain

fun SaveScript.toDomain(): SaveScriptRequestDTO {
    return SaveScriptRequestDTO(
        content = this.content,
        language = this.language,
        issueSignedUrl = this.issueSignedUrl
    )
}

fun ConnectSession.toDomain(): ConnectSessionRequestDTO{
    return ConnectSessionRequestDTO(
        gcsUri = this.gcsUri
    )
}

fun FillerTimeline.toDomain(): FillerTimelineDomain {
    return FillerTimelineDomain(
        binEnd = this.binEnd,
        binStart = this.binStart,
        fillerCount = this.fillerCount,
        fillerDurationSec = this.fillerDurationSec
    )
}

fun Example.toDomain(): ExampleDomain {
    return ExampleDomain(
        asr = this.asr,
        next2 = this.next2,
        prev2 = this.prev2,
        target = this.target
    )
}

fun Pronounce.toDomain(): PronounceDomain {
    return PronounceDomain(
        coverage = this.coverage,
        dels = this.dels,
        // 🌟 중첩된 리스트 매핑
        examples = this.examples.map { it.toDomain() },
        ins = this.ins,
        matchedWords = this.matchedWords,
        subs = this.subs,
        totalWords = this.totalWords,
        wer = this.wer
    )
}

fun ScoreWeights.toDomain(): ScoreWeightsDomain {
    return ScoreWeightsDomain(
        additionalProp1 = this.additionalProp1,
        additionalProp2 = this.additionalProp2,
        additionalProp3 = this.additionalProp3
    )
}

fun ScoreMetrics.toDomain(): ScoreMetricsDomain {
    return ScoreMetricsDomain(
        baselineSpeedVarOkPct = this.baselineSpeedVarOkPct,
        baselineSpeedVarWarnPct = this.baselineSpeedVarWarnPct,
        baselineWpm = this.baselineWpm,
        category = this.category,
        fillerDurationPct = this.fillerDurationPct,
        fillerPerMin = this.fillerPerMin,
        fillerScore = this.fillerScore,
        fillerTotalCount = this.fillerTotalCount,
        fillerTotalDurationSec = this.fillerTotalDurationSec,
        finalScore = this.finalScore,
        pronAccuracyPct = this.pronAccuracyPct,
        pronScore = this.pronScore,
        pronWerPct = this.pronWerPct,
        // 🌟 단일 객체 매핑
        scoreWeights = this.scoreWeights.toDomain(),
        speedScore = this.speedScore,
        speedScoreMeanComponent = this.speedScoreMeanComponent,
        speedScoreStabilityComponent = this.speedScoreStabilityComponent,
        userMeanDeltaPctVsBaseline = this.userMeanDeltaPctVsBaseline,
        userSpeedVarP50Pct = this.userSpeedVarP50Pct,
        userSpeedVarP90Pct = this.userSpeedVarP90Pct,
        userWpm = this.userWpm
    )
}

fun SpeedTimeline.toDomain(): SpeedTimelineDomain {
    return SpeedTimelineDomain(
        end = this.end,
        sps = this.sps,
        start = this.start,
        syllables = this.syllables,
        t = this.t,
        words = this.words,
        wpm = this.wpm,
        wps = this.wps
    )
}