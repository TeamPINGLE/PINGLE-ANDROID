package org.sopt.pingle.domain.model

data class PlanEntity(
    val category: String,
    val name: String,
    val startAt: String,
    val endAt: String,
    val x: Double,
    val y: Double,
    val address: String,
    val location: String,
    val maxParticipants: Int,
    val chatLink: String,
)
