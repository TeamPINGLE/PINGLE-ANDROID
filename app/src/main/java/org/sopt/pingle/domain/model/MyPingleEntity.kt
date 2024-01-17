package org.sopt.pingle.domain.model

data class MyPingleEntity(
    val id: Int,
    val category: String,
    val name: String,
    val ownerName: String,
    val location: String,
    val dDay: String,
    val date: String,
    val startAt: String,
    val endAt: String,
    val maxParticipants: Int,
    val curParticipants: Int,
    val isOwner: Boolean,
    val chatLink: String
)
