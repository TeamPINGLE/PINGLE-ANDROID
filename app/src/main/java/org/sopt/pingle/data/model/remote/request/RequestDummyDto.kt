package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDummyDto(
    @SerialName("id")
    val id: String
)
