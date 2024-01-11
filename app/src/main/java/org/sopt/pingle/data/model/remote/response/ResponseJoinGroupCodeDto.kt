package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.ResponseJoinGroupCodeEntity

@Serializable
data class ResponseJoinGroupCodeDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
) {
    fun toResponseJoinGroupCode() = ResponseJoinGroupCodeEntity(
        id = id,
        name = name
    )
}