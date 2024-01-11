package org.sopt.pingle.presentation.mapper

import androidx.databinding.Observable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType

fun PinEntity.toMarkerModel(): MarkerModel {
    val markerModel = MarkerModel(
        Marker().apply {
            position = LatLng(x, y)
            isHideCollidedMarkers = true
            icon = CategoryType.fromString(category).toMarkerIcon(false)
        }
    )

    markerModel.isSelected.addOnPropertyChangedCallback(
        object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                with(markerModel.marker) {
                    icon = CategoryType.fromString(category).toMarkerIcon(markerModel.isSelected.get())
                }
            }
        }
    )

    return markerModel
}
