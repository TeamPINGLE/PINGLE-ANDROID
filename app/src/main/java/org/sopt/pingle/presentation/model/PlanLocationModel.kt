package org.sopt.pingle.presentation.model

import androidx.databinding.ObservableBoolean
import org.sopt.pingle.domain.model.PlanLocationEntity

data class PlanLocationModel(
    val planLocation: PlanLocationEntity,
    var isSelected: ObservableBoolean = ObservableBoolean(false)
)
