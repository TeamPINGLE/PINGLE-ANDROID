package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity

@Serializable
data class ResponseNewGroupKeywordsDto(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String
) {
    fun toNewGroupKeywordsEntity() = NewGroupKeywordsEntity(
        name = this.name,
        value = this.value
    )
}
