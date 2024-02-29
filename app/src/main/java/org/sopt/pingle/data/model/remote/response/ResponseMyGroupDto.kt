package org.sopt.pingle.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sopt.pingle.domain.model.MyGroupEntity

@Serializable
data class ResponseMyGroupDto(
    @SerialName("id")
    val id: Int,
    @SerialName("keyword")
    val keyword: String,
    @SerialName("name")
    val name: String,
    @SerialName("meetingCount")
    val meetingCount: Int,
    @SerialName("participantCount")
    val participantCount: Int,
    @SerialName("isOwner")
    val isOwner: Boolean,
    @SerialName("code")
    val code: String
) {
    fun toResponseMyGroupEntity() = MyGroupEntity(
        id = id,
        keyword = keyword,
        name = name,
        meetingCount = meetingCount.toString(),
        participantCount = participantCount.toString(),
        isOwner = isOwner,
        code = code
    )
}
