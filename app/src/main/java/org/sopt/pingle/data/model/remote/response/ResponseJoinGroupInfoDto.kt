package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.JoinGroupInfoEntity

@Serializable
data class ResponseJoinGroupInfoDto(
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
    fun toJoinGroupCodeEntity() = JoinGroupInfoEntity(
        id = this.id,
        keyword = this.keyword,
        name = this.name,
        meetingCount = this.meetingCount,
        participantCount = this.participantCount
    )
}
