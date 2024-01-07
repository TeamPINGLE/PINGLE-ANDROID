package org.sopt.pingle.presentation.ui.main.home.map

import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.mapper.toMarkerModel
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType

class MapViewModel() : ViewModel() {
    val dummyPinList = listOf(
        PinEntity(
            id = 1,
            x = 126.9275108,
            y = 37.5262935,
            category = "PLAY",
            meetingCount = 1
        ),
        PinEntity(
            id = 2,
            x = 126.9283122,
            y = 37.5259168,
            category = "STUDY",
            meetingCount = 2
        ),
        PinEntity(
            id = 3,
            x = 126.9276423,
            y = 37.5258711,
            category = "MULTI",
            meetingCount = 1
        ),
        PinEntity(
            id = 4,
            x = 126.9286719,
            y = 37.5253629,
            category = "OTHERS",
            meetingCount = 2
        )
    )

    var dummyPingle = PingleEntity(
        id = 1,
        category = "PLAY",
        name = "핑글핑글핑글 ~",
        ownerName = "배지현",
        location = "길음역",
        date = "2023-01-06",
        startAt = "10:30:00",
        endAt = "22:34:00",
        maxParticipants = 5,
        curParticipants = 4,
        isParticipating = false,
        chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID"
    )

    private val _cameraPoint = MutableStateFlow<LatLng>(LatLng(DEFAULT_LAT, DEFAULT_LNG))
    val cameraPoint get() = _cameraPoint.asStateFlow()

    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private var _markerList = MutableStateFlow<List<MarkerModel>>(emptyList())
    val markerList get() = _markerList.asStateFlow()

    private var _selectedMarkerPosition = MutableStateFlow(DEFAULT_SELECTED_MARKER_POSITION)
    val selectedMarkerPosition = _selectedMarkerPosition.asStateFlow()

    init {
        setMarkerList()
    }

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    fun setMarkerList() {
        _markerList.value = dummyPinList.map { pinEntity ->
            pinEntity.toMarkerModel()
        }
    }

    fun handleMarkerClick(position: Int) {
        setMarkerIsSelected(position)
        if (_selectedMarkerPosition.value != DEFAULT_SELECTED_MARKER_POSITION) setMarkerIsSelected(
            _selectedMarkerPosition.value
        )
        _selectedMarkerPosition.value = position
    }

    fun clearSelectedMarkerPosition() {
        if (_selectedMarkerPosition.value != DEFAULT_SELECTED_MARKER_POSITION) {
            setMarkerIsSelected(_selectedMarkerPosition.value)
            _selectedMarkerPosition.value = DEFAULT_SELECTED_MARKER_POSITION
        }
    }

    private fun setMarkerIsSelected(position: Int) {
        markerList.value[position].isSelected.set(!markerList.value[position].isSelected.get())
    }

    fun cancelPingle() {
        dummyPingle = PingleEntity(
            id = 1,
            category = "PLAY",
            name = "핑글핑글핑글 ~",
            ownerName = "배지현",
            location = "길음역",
            date = "2023-01-06",
            startAt = "10:30:00",
            endAt = "22:34:00",
            maxParticipants = 5,
            curParticipants = 4,
            isParticipating = false,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID"
        )
    }

    fun joinPingle() {
        dummyPingle = PingleEntity(
            id = 1,
            category = "PLAY",
            name = "핑글핑글핑글 ~",
            ownerName = "배지현",
            location = "길음역",
            date = "2023-01-06",
            startAt = "10:30:00",
            endAt = "22:34:00",
            maxParticipants = 5,
            curParticipants = 5,
            isParticipating = true,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID"
        )
    }
}
