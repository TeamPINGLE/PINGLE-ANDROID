package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseReissueDto(
    val accessToken: String,
    val refreshToken: String
)
