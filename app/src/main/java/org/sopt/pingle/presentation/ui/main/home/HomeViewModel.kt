package org.sopt.pingle.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.usecase.DeletePingleCancelUseCase
import org.sopt.pingle.domain.usecase.DeletePingleDeleteUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.domain.usecase.PostPingleJoinUseCase
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getPinListWithoutFilteringUseCase: GetPinListWithoutFilteringUseCase,
    private val getPingleListUseCase: GetPingleListUseCase,
    private val postPingleJoinUseCase: PostPingleJoinUseCase,
    private val deletePingleCancelUseCase: DeletePingleCancelUseCase,
    private val deletePingleDeleteUseCase: DeletePingleDeleteUseCase
) : ViewModel() {
    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private val _pinEntityListState = MutableStateFlow<UiState<List<PinEntity>>>(UiState.Empty)
    val pinEntityListState get() = _pinEntityListState.asStateFlow()

    private var _markerModelData =
        MutableStateFlow<Pair<Int, MutableList<MarkerModel>>>(
            Pair(
                DEFAULT_SELECTED_MARKER_POSITION,
                mutableListOf()
            )
        )

    val markerModelData get() = _markerModelData.asStateFlow()

    private val _pingleListState = MutableSharedFlow<UiState<Pair<Long, List<PingleEntity>>>>()
    val pingleListState get() = _pingleListState.asSharedFlow()

    private val _pingleParticipationState = MutableSharedFlow<UiState<Unit?>>()
    val pingleParticipationState get() = _pingleParticipationState.asSharedFlow()

    private val _pingleDeleteState = MutableSharedFlow<UiState<Unit?>>()
    val pingleDeleteState get() = _pingleDeleteState.asSharedFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    private fun setMarkerModelListIsSelected(position: Int) {
        _markerModelData.value.second[position].isSelected.set(!_markerModelData.value.second[position].isSelected.get())
    }

    private fun getMarkerModelSelected(position: Int) =
        _markerModelData.value.second[position].isSelected.get()

    fun clearMarkerModelData() {
        _markerModelData.value.second.forEach { it.marker.map = null }
        _markerModelData.value.second.clear()
        _markerModelData.value =
            Pair(DEFAULT_SELECTED_MARKER_POSITION, _markerModelData.value.second)
    }

    fun addMarkerModelList(markerEntity: MarkerModel) {
        _markerModelData.value.second.add(markerEntity)
    }

    fun updateMarkerModelListSelectedValue(position: Int) {
        when (_markerModelData.value.first) {
            DEFAULT_SELECTED_MARKER_POSITION -> setMarkerModelListIsSelected(position)
            position -> Unit
            else -> {
                if (getMarkerModelSelected(_markerModelData.value.first)) {
                    setMarkerModelListIsSelected(
                        _markerModelData.value.first
                    )
                }
                if (!getMarkerModelSelected(position)) setMarkerModelListIsSelected(position)
            }
        }
        _markerModelData.value = Pair(position, _markerModelData.value.second)
    }

    fun clearSelectedMarkerPosition() {
        if (_markerModelData.value.first != DEFAULT_SELECTED_MARKER_POSITION) {
            if (getMarkerModelSelected(_markerModelData.value.first)) {
                setMarkerModelListIsSelected(
                    _markerModelData.value.first
                )
            }
            _markerModelData.value =
                Pair(DEFAULT_SELECTED_MARKER_POSITION, _markerModelData.value.second)
        }
    }

    fun getPinListWithoutFilter() {
        viewModelScope.launch {
            _pinEntityListState.value = UiState.Loading
            runCatching {
                getPinListWithoutFilteringUseCase(
                    teamId = localStorage.groupId.toLong(),
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
                    teamId = localStorage.groupId.toLong(),
                    pinId = pinId,
                    category = category.value?.name
                ).collect() { pingleList ->
                    _pingleListState.emit(UiState.Success(Pair(pinId, pingleList)))
                }
            }.onFailure { exception ->
                _pingleListState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun postPingleJoin(meetingId: Long) {
        viewModelScope.launch {
            _pingleParticipationState.emit(UiState.Loading)
            runCatching {
                postPingleJoinUseCase(
                    meetingId = meetingId
                ).collect() { data ->
                    _pingleParticipationState.emit(UiState.Success(data))
                }
            }.onFailure { exception: Throwable ->
                _pingleParticipationState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun deletePingleCancel(meetingId: Long) {
        viewModelScope.launch {
            _pingleParticipationState.emit(UiState.Loading)
            runCatching {
                deletePingleCancelUseCase(
                    meetingId = meetingId
                ).collect() { data ->
                    _pingleParticipationState.emit(UiState.Success(data))
                }
            }.onFailure { exception: Throwable ->
                _pingleParticipationState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun deletePingleDelete(meetingId: Long) {
        viewModelScope.launch {
            _pingleDeleteState.emit(UiState.Loading)
            runCatching {
                deletePingleDeleteUseCase(meetingId = meetingId).collect() { data ->
                    _pingleDeleteState.emit(UiState.Success(data))
                }
            }.onFailure { exception: Throwable ->
                _pingleDeleteState.emit(UiState.Error(exception.message))
            }
        }
    }

    companion object {
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
    }
}
