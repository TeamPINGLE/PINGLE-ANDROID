package org.sopt.pingle.domain.model

import androidx.databinding.ObservableBoolean

data class JoinGroupSearchEntity(
    val id: Int,
    val name: String,
    val keyword: String,
    var isSelected: ObservableBoolean = ObservableBoolean(false)
)
