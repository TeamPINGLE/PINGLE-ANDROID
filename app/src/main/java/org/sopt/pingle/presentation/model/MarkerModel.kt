package org.sopt.pingle.presentation.model

import androidx.databinding.ObservableBoolean
import com.naver.maps.map.overlay.Marker

data class MarkerModel(
    val marker: Marker,
    var isSelected: ObservableBoolean = ObservableBoolean(false)
)
