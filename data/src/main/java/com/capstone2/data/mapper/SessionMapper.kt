package com.capstone2.data.mapper

import com.capstone2.data.model.session.ConnectSessionRequestDTO
import com.capstone2.data.model.session.SaveScriptRequestDTO
import com.capstone2.domain.model.session.ConnectSession
import com.capstone2.domain.model.session.SaveScript

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