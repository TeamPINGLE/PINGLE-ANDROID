package org.sopt.pingle.presentation.model

import androidx.databinding.ObservableBoolean
import org.sopt.pingle.domain.model.PingleEntity

data class MainListPingleModel(
    val pingleEntity: PingleEntity,
    var isExpanded: ObservableBoolean = ObservableBoolean(false)
)
