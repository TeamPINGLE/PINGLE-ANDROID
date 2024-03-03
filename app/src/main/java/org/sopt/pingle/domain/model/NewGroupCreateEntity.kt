package org.sopt.pingle.domain.model

data class NewGroupCreateEntity(
    val id: String,
    val name: String,
    val email: String,
    val keyword: String,
    val code: String
)
