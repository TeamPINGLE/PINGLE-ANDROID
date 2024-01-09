package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

data class ResponseJoinGroupSearchDto(
    @SerialName("code")
    val code: Int,
    @SerialName("code")
    val message: String,
    @SerialName("code")
    val data: List<Data>
) {
    data class Data(
        @SerialName("code")
        val id: Int,
        @SerialName("code")
        val name: String,
        @SerialName("code")
        val keyword: String
    ) {
        fun toJoinGroupSearchEntity() = JoinGroupSearchEntity(
            id = this.id,
            keyword = this.keyword,
            name = this.name
        )
    }
}
