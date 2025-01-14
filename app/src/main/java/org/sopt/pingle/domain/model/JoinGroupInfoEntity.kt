package org.sopt.pingle.domain.model

data class JoinGroupInfoEntity(
    val id: Int,
    val keyword: String,
    val name: String,
    val meetingCount: Int,
    val participantCount: Int
)
