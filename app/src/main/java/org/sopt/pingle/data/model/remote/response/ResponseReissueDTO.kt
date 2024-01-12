package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseReissueDTO(
    val accessToken: String,
    val refreshToken: String
)
