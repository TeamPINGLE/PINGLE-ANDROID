package org.sopt.pingle.presentation.mapper

import androidx.databinding.Observable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType

fun PinEntity.toMarkerModel(): MarkerModel {
    val markerModel = MarkerModel(
        id = this.id,
        Marker().apply {
            position = LatLng(y, x)
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
