package org.sopt.pingle.presentation.ui.main.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.presentation.mapper.toMarkerModel
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPinListWithoutFilteringUseCase: GetPinListWithoutFilteringUseCase
) : ViewModel() {
    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private val _markerListState = MutableStateFlow<UiState<List<MarkerModel>>>(UiState.Empty)
    val markerListState get() = _markerListState.asStateFlow()

    private val _markerList = mutableListOf<Marker>()

    private var _selectedMarkerPosition = MutableStateFlow(DEFAULT_SELECTED_MARKER_POSITION)
    val selectedMarkerPosition = _selectedMarkerPosition.asStateFlow()

    private fun setMarkerIsSelected(position: Int) {
        // TODO 마커 선택값 재설정
    }

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    fun clearMarkerList() {
        _markerList.forEach { it.map = null }
        _markerList.clear()
    }

    fun addMarkerList(marker: Marker) {
        _markerList.add(marker)
    }

    fun handleMarkerClick(position: Int) {
        setMarkerIsSelected(position)
        if (_selectedMarkerPosition.value != DEFAULT_SELECTED_MARKER_POSITION) {
            setMarkerIsSelected(
                _selectedMarkerPosition.value
            )
        }
        _selectedMarkerPosition.value = position
    }

    fun clearSelectedMarkerPosition() {
        if (_selectedMarkerPosition.value != DEFAULT_SELECTED_MARKER_POSITION) {
            setMarkerIsSelected(_selectedMarkerPosition.value)
            _selectedMarkerPosition.value = DEFAULT_SELECTED_MARKER_POSITION
        }
    }

    fun getPinListWithoutFilter() {
        viewModelScope.launch {
            _markerListState.value = UiState.Loading
            runCatching {
                getPinListWithoutFilteringUseCase(
                    teamId = TEAM_ID,
                    category = category.value?.name
                ).collect() { pinList ->
                    _markerListState.value = UiState.Success(
                        pinList.map { pinEntity ->
                            pinEntity.toMarkerModel()
                        }
                    )
                }
            }.onFailure { exception: Throwable ->
                _markerListState.value = UiState.Error(exception.message)
            }
        }
    }

    companion object {
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
        const val TEAM_ID = 1L
    }
}
