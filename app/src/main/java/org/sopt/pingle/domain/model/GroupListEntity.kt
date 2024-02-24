package org.sopt.pingle.domain.model

data class GroupListEntity(
    val id: Int,
    val keyword: String,
    val name: String,
    val meetingCount: String,
    val participantCount: String,
    val isOwner: Boolean,
    val code: String
)
