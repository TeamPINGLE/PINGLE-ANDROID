package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import org.sopt.pingle.domain.model.JoinGroupCodeEntity

data class ResponseJoinGroupCodeDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Data
) {
    data class Data(
        @SerialName("id")
        val id: Int,
        @SerialName("keyword")
        val keyword: String,
        @SerialName("name")
        val name: String,
        @SerialName("meetingCount")
        val meetingCount: Int,
        @SerialName("participantCount")
        val participantCount: Int
    ) {
        fun toJoinGroupCodeEntity() = JoinGroupCodeEntity(
            id = this.id,
            keyword = this.keyword,
            name = this.name,
            meetingCount = this.meetingCount,
            participantCount = this.participantCount
        )
    }
}
