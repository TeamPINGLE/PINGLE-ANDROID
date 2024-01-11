package org.sopt.pingle.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.RequestJoinGroupCodeEntity

@Serializable
data class RequestJoinGroupCodeDto(
    @SerialName("code")
    val code: String
) {
    fun toRequestJoinGroupCode() = RequestJoinGroupCodeEntity(
        code = this.code
    )
}
