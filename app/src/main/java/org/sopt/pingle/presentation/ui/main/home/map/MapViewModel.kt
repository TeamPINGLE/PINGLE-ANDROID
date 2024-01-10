package org.sopt.pingle.presentation.ui.main.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPinListWithoutFilteringUseCase: GetPinListWithoutFilteringUseCase
) : ViewModel() {
    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private val _pinEntityListState = MutableStateFlow<UiState<List<PinEntity>>>(UiState.Empty)
    val pinEntityListState get() = _pinEntityListState.asStateFlow()

    val _markerModelList = mutableListOf<MarkerModel>()

    private var _selectedMarkerPosition = MutableStateFlow(DEFAULT_SELECTED_MARKER_POSITION)
    val selectedMarkerPosition get() = _selectedMarkerPosition.asStateFlow()

    private val _pingleListState = MutableStateFlow<UiState<List<PingleEntity>>>(UiState.Empty)
    val pingleListState get() = _pingleListState.asStateFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    fun clearMarkerList() {
        _markerModelList.forEach { it.marker.map = null }
        _markerModelList.clear()
    }

    fun addMarkerList(markerEntity: MarkerModel) {
        _markerModelList.add(markerEntity)
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
            _pinEntityListState.value = UiState.Loading
            runCatching {
                getPinListWithoutFilteringUseCase(
                    teamId = TEAM_ID,
                    category = category.value?.name
                ).collect() { pinList ->
                    _pinEntityListState.value = UiState.Success(pinList)
                }
            }.onFailure { exception: Throwable ->
                _pinEntityListState.value = UiState.Error(exception.message)
            }
        }
    }

    companion object {
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
        const val TEAM_ID = 1L
    }
}
