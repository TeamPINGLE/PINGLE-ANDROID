package org.sopt.pingle.presentation.mapper

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import org.sopt.pingle.R
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.presentation.type.CategoryType

fun PinEntity.toMarker(): Marker = Marker().apply {
    position = LatLng(y, x)
    isHideCollidedMarkers = true
    icon = OverlayImage.fromResource(
        when (category) {
            CategoryType.PLAY.toString() -> R.drawable.ic_map_marker_play_small
            CategoryType.STUDY.toString() -> R.drawable.ic_map_marker_study_small
            CategoryType.MULTI.toString() -> R.drawable.ic_map_marker_multi_small
            else -> R.drawable.ic_map_marker_other_small
        }
    )
}
