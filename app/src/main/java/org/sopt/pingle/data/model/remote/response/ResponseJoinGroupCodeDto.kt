package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.GroupEntity

@Serializable
data class ResponseJoinGroupCodeDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
) {
    fun toResponseJoinGroupCode() = GroupEntity(
        id = id,
        name = name
    )
}
