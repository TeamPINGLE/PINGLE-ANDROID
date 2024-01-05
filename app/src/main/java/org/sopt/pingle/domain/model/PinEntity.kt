package org.sopt.pingle.domain.model

data class PinEntity(
    val id: Long,
    val x: Double,
    val y: Double,
    val category: String,
    val meetingCount: Int
)
