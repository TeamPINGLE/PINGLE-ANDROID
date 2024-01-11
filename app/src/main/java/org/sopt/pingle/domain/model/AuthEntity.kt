package org.sopt.pingle.domain.model

data class AuthEntity(
    val accessToken: String,
    val refreshToken: String
)
