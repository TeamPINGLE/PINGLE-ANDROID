package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.NewGroupKeywordEntity

@Serializable
data class ResponseNewGroupKeywordsDto(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String
) {
    fun toNewGroupKeywordsEntity() = NewGroupKeywordEntity(
        name = this.name,
        value = this.value
    )
}
