package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNewGroupCreateDto(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("keyword")
    val keyword: String
)
