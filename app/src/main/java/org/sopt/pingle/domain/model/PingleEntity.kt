package org.sopt.pingle.domain.model

data class PingleEntity(
    val id: Long,
    val category: String,
    val name: String,
    val ownerName: String,
    val location: String,
    val date: String,
    val startAt: String,
    val endAt: String,
    val maxParticipants: Int,
    val curParticipants: Int,
    val isParticipating: Boolean,
    val chatLink: String
)
