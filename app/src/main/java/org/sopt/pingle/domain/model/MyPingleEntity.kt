package org.sopt.pingle.domain.model

data class MyPingleEntity(
    val id: String,
    val category: String,
    val curParticipants: String,
    val name: String,
    val ownerName: String,
    val location: String,
    val dDay: String,
    val date: String,
    val startAt: String,
    val endAt: String,
    val maxParticipants: String,
    val isOwner: Boolean
)
