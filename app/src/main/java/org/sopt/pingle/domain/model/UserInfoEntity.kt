package org.sopt.pingle.domain.model

data class UserInfoEntity(
    val id: Int,
    val name: String,
    val provider: String,
    val email: String,
    val groups: List<GroupEntity>
)
