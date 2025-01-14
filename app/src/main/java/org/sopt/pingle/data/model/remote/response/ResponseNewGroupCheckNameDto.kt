package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity

@Serializable
data class ResponseNewGroupCheckNameDto(
    @SerialName("result")
    val result: Boolean
) {
    fun toNewGroupCheckNameEntity() = NewGroupCheckNameEntity(
        result = this.result
    )
}
