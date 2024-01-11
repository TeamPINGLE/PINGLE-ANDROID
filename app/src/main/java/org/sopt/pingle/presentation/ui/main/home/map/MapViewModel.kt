package org.sopt.pingle.presentation.ui.main.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.domain.usecase.PostPingleParticipationUseCase
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPinListWithoutFilteringUseCase: GetPinListWithoutFilteringUseCase,
    private val getPingleListUseCase: GetPingleListUseCase,
    private val postPingleParticipationUseCase: PostPingleParticipationUseCase
) : ViewModel() {
    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private val _pinEntityListState = MutableStateFlow<UiState<List<PinEntity>>>(UiState.Empty)
    val pinEntityListState get() = _pinEntityListState.asStateFlow()

    private val _markerModelList = mutableListOf<MarkerModel>()

    private var _selectedMarkerPosition = MutableStateFlow(DEFAULT_SELECTED_MARKER_POSITION)
    val selectedMarkerPosition get() = _selectedMarkerPosition.asStateFlow()

    private val _pingleListState = MutableSharedFlow<UiState<List<PingleEntity>>>()
    val pingleListState get() = _pingleListState.asSharedFlow()

    private val _pingleParticipationState = MutableSharedFlow<UiState<Unit?>>()
    val pingleParticipationState get() = _pingleParticipationState.asSharedFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    private fun setMarkerModelIsSelected(position: Int) {
        _markerModelList[position].isSelected.set(!_markerModelList[position].isSelected.get())
    }

    fun clearMarkerList() {
        _markerModelList.forEach { it.marker.map = null }
        _markerModelList.clear()
    }

    fun addMarkerList(markerEntity: MarkerModel) {
        _markerModelList.add(markerEntity)
    }

    private fun getMarkerModelModelSelected(position: Int) =
        _markerModelList[position].isSelected.get()

    fun updateMarkerModelListSelectedValue(position: Int) {
        when (_selectedMarkerPosition.value) {
            DEFAULT_SELECTED_MARKER_POSITION -> setMarkerModelIsSelected(position)
            position -> Unit
            else -> {
                if (getMarkerModelModelSelected(_selectedMarkerPosition.value)) {
                    setMarkerModelIsSelected(
                        _selectedMarkerPosition.value
                    )
                }
                if (!getMarkerModelModelSelected(position)) setMarkerModelIsSelected(position)
            }
        }
        _selectedMarkerPosition.value = position
    }

    fun clearSelectedMarkerPosition() {
        if (_selectedMarkerPosition.value != DEFAULT_SELECTED_MARKER_POSITION) {
            if (getMarkerModelModelSelected(_selectedMarkerPosition.value)) {
                setMarkerModelIsSelected(
                    _selectedMarkerPosition.value
                )
            }
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

    fun getPingleList(pinId: Long) {
        viewModelScope.launch {
            _pingleListState.emit(UiState.Loading)
            runCatching {
                getPingleListUseCase(
                    teamId = TEAM_ID,
                    pinId = pinId
                ).collect() { pingleList ->
                    _pingleListState.emit(UiState.Success(pingleList))
                }
            }.onFailure { exception ->
                _pingleListState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun postPingleParticipation(meetingId: Long) {
        viewModelScope.launch {
            _pingleListState.emit(UiState.Loading)
            runCatching {
                postPingleParticipationUseCase(
                    meetingId = meetingId
                ).collect() { data ->
                    _pingleParticipationState.emit(UiState.Success(data))
                }
            }.onFailure { exception: Throwable ->
                _pingleParticipationState.emit(UiState.Error(exception.message))
            }
        }
    }

    companion object {
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
        const val TEAM_ID = 1L
    }
}
