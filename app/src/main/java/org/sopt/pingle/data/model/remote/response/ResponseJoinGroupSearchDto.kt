package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

@Serializable
data class ResponseJoinGroupSearchDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("keyword")
    val keyword: String
) {
    fun toJoinGroupSearchEntity() = JoinGroupSearchEntity(
        id = this.id,
        keyword = this.keyword,
        name = this.name
    )
}
