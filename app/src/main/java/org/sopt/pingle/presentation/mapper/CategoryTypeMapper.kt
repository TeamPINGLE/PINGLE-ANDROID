package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment

fun CategoryType.toMarkerIcon(isSelected: Boolean) =
    when (this) {
        CategoryType.PLAY -> if (isSelected) MapFragment.OVERLAY_IMAGE_PIN_PLAY_ACTIVE else MapFragment.OVERLAY_IMAGE_PIN_PLAY_DEFAULT
        CategoryType.STUDY -> if (isSelected) MapFragment.OVERLAY_IMAGE_PIN_STUDY_ACTIVE else MapFragment.OVERLAY_IMAGE_PIN_STUDY_DEFAULT
        CategoryType.MULTI -> if (isSelected) MapFragment.OVERLAY_IMAGE_PIN_MULTI_ACTIVE else MapFragment.OVERLAY_IMAGE_PIN_MULTI_DEFAULT
        CategoryType.OTHERS -> if (isSelected) MapFragment.OVERLAY_IMAGE_PIN_OTHERS_ACTIVE else MapFragment.OVERLAY_IMAGE_PIN_OTHERS_DEFAULT
    }
