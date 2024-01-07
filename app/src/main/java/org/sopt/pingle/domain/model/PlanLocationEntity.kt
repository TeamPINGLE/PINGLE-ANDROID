package org.sopt.pingle.domain.model

import androidx.databinding.ObservableBoolean

data class PlanLocationEntity(
    val location: String,
    val address: String,
    val x: Double,
    val y: Double,
    var isSelected: ObservableBoolean = ObservableBoolean(false)
)
