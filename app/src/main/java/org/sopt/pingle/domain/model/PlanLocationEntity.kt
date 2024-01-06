package org.sopt.pingle.domain.model

data class PlanLocationEntity(
    val location: String,
    val address: String,
    val x: Double,
    val y: Double,
    var isSelected: Boolean = false,
)
