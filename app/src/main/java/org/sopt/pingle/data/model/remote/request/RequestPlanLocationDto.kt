package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPlanLocationDto(
    @SerialName("search")
    val search: String
)