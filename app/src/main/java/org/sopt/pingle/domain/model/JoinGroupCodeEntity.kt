package org.sopt.pingle.domain.model

data class JoinGroupCodeEntity(
    val id: Int,
    val keyword: String,
    val name: String,
    val meetingCount: Int,
    val participantCount: Int
)
