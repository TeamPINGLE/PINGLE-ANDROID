package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMainListDto(
    @SerialName("searchCount")
    val searchCount: Int,
    @SerialName("meetings")
    val meetings: List<ResponsePingleDto>
)
