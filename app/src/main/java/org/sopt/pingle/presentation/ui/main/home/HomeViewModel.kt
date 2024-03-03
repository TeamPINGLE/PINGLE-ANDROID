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
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.type.MainListOrderType
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

    private val _homeViewType = MutableStateFlow<HomeViewType>(HomeViewType.MAP)
    val homeViewType get() = _homeViewType.asStateFlow()

    private var _searchWord = MutableStateFlow<String?>(null)
    val searchWord get() = _searchWord.asStateFlow()

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

    private val _mainListOrderType = MutableStateFlow(MainListOrderType.NEW)
    val mainListOrderType get() = _mainListOrderType.asStateFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    fun setHomeViewType(homeViewType: HomeViewType) {
        _homeViewType.value = homeViewType
    }

    fun setSearchWord(searchWord: String?) {
        _searchWord.value = searchWord
    }

    fun clearSearchWord() {
        _searchWord.value = null
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

    fun setMainListOrderType(mainListOrderType: MainListOrderType) {
        _mainListOrderType.value = mainListOrderType
    }

    fun getGroupName(): String = localStorage.groupName

    fun getPinListWithoutFilter() {
        viewModelScope.launch {
            _pinEntityListState.value = UiState.Loading
            runCatching {
                getPinListWithoutFilteringUseCase(
                    teamId = localStorage.groupId.toLong(),
                    category = category.value?.name,
                    searchWord = searchWord.value
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

    val dummyPingleList = listOf<PingleEntity>(
        PingleEntity(
            id = 1L,
            category = "STUDY",
            name = "강남 모각작팟을 구합니다!!!!",
            ownerName = "박소현",
            location = "하얀집 2호점",
            date = "2023-12-31",
            startAt = "17:00:00",
            endAt = "23:00:00",
            maxParticipants = 99,
            curParticipants = 11,
            isParticipating = false,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = false
        ),
        PingleEntity(
            id = 2L,
            category = "MULTI",
            name = "모각공하고 곱창먹자",
            ownerName = "하지은",
            location = "푸지미곱창",
            date = "2024-02-23",
            startAt = "14:00:00",
            endAt = "23:00:00",
            maxParticipants = 12,
            curParticipants = 4,
            isParticipating = true,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = false
        ),
        PingleEntity(
            id = 3L,
            category = "OTHERS",
            name = "국민대 졸업하는 김승연 축하하러 올사람",
            ownerName = "김승연",
            location = "국민대학교",
            date = "2024-02-14",
            startAt = "14:30:00",
            endAt = "21:30:00",
            maxParticipants = 14,
            curParticipants = 14,
            isParticipating = false,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = false
        ),
        PingleEntity(
            id = 4L,
            category = "PLAY",
            name = "건빵 QA",
            ownerName = "배지현",
            location = "서울역",
            date = "2024-02-23",
            startAt = "18:30:00",
            endAt = "21:30:00",
            maxParticipants = 13,
            curParticipants = 13,
            isParticipating = true,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = false
        ),
        PingleEntity(
            id = 5L,
            category = "PLAY",
            name = "안핑이들 집합 ㅋ.ㅋ",
            ownerName = "배지현",
            location = "포어플랜",
            date = "2024-02-27",
            startAt = "19:00:00",
            endAt = "23:00:00",
            maxParticipants = 4,
            curParticipants = 4,
            isParticipating = true,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = true
        ),
        PingleEntity(
            id = 6L,
            category = "OTHERS",
            name = "핑글",
            ownerName = "배지현",
            location = "핑글핑글",
            date = "2024-02-29",
            startAt = "01:00:00",
            endAt = "03:00:00",
            maxParticipants = 24,
            curParticipants = 13,
            isParticipating = true,
            chatLink = "https://github.com/TeamPINGLE/PINGLE-ANDROID",
            isOwner = true
        )
    )

    companion object {
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
    }
}
