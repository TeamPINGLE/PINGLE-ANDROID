package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.NewGroupCreateEntity

@Serializable
data class ResponseNewGroupCreateDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("keyword")
    val keyword: String,
    @SerialName("code")
    val code: String
) {
    fun toNewGroupCreateEntity() = NewGroupCreateEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        keyword = this.keyword,
        code = this.code
    )
}
