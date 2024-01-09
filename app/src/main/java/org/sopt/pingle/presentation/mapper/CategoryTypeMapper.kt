package org.sopt.pingle.presentation.mapper

import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment

fun CategoryType.toMarkerIcon(isSelected: Boolean) =
    when (this) {
        CategoryType.PLAY -> if (isSelected) MapFragment.OVERLAY_IMAGE_PLAY_BIG else MapFragment.OVERLAY_IMAGE_PLAY_SMALL
        CategoryType.STUDY -> if (isSelected) MapFragment.OVERLAY_IMAGE_STUDY_BIG else MapFragment.OVERLAY_IMAGE_STUDY_SMALL
        CategoryType.MULTI -> if (isSelected) MapFragment.OVERLAY_IMAGE_MULTI_BIG else MapFragment.OVERLAY_IMAGE_MULTI_SMALL
        CategoryType.OTHERS -> if (isSelected) MapFragment.OVERLAY_IMAGE_OTHER_BIG else MapFragment.OVERLAY_IMAGE_OTHER_SMALL
    }
