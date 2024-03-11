package org.sopt.pingle.presentation.ui.main.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.usecase.DeletePingleCancelUseCase
import org.sopt.pingle.domain.usecase.DeletePingleDeleteUseCase
import org.sopt.pingle.domain.usecase.GetMainListPingleListUseCase
import org.sopt.pingle.domain.usecase.GetMapPingleListUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.PostPingleJoinUseCase
import org.sopt.pingle.presentation.mapper.toMainListPingleModel
import org.sopt.pingle.presentation.model.MainListPingleModel
import org.sopt.pingle.presentation.model.MarkerModel
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.HomeViewType
import org.sopt.pingle.presentation.type.MainListOrderType
import org.sopt.pingle.util.base.NullableBaseResponse
import org.sopt.pingle.util.view.UiState
import retrofit2.HttpException

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val deletePingleCancelUseCase: DeletePingleCancelUseCase,
    private val deletePingleDeleteUseCase: DeletePingleDeleteUseCase,
    private val getMainListPingleListUseCase: GetMainListPingleListUseCase,
    private val getMapPingleListUseCase: GetMapPingleListUseCase,
    private val getPinListWithoutFilteringUseCase: GetPinListWithoutFilteringUseCase,
    private val postPingleJoinUseCase: PostPingleJoinUseCase
) : ViewModel() {
    private val sharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == GROUP_ID) {
                setHomeViewType(HomeViewType.MAP)
                clearCategory()
                clearSearchWord()
                clearMarkerModelData()
                clearSelectedMarkerPosition()
            }
        }

    init {
        localStorage.sharedPreference.registerOnSharedPreferenceChangeListener(
            sharedPreferenceChangeListener
        )
    }

    override fun onCleared() {
        localStorage.sharedPreference.unregisterOnSharedPreferenceChangeListener(
            sharedPreferenceChangeListener
        )
        super.onCleared()
    }

    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    private val _homeViewType = MutableStateFlow<HomeViewType>(HomeViewType.MAP)
    val homeViewType get() = _homeViewType.asStateFlow()

    private var _searchWord = MutableStateFlow<String?>(null)
    val searchWord get() = _searchWord.asStateFlow()

    private var _lastSearchWord: String? = null
    val lastSearchWord get() = _lastSearchWord

    private val _pinEntityListState =
        MutableStateFlow<UiState<Pair<Boolean, List<PinEntity>>>>(UiState.Empty)
    val pinEntityListState get() = _pinEntityListState.asStateFlow()

    private var _markerModelData =
        MutableStateFlow<Pair<Int, MutableList<MarkerModel>>>(
            Pair(
                DEFAULT_SELECTED_MARKER_POSITION,
                mutableListOf()
            )
        )

    val markerModelData get() = _markerModelData.asStateFlow()

    private val _mapPingleListState = MutableSharedFlow<UiState<Pair<Long, List<PingleEntity>>>>()
    val mapPingleListState get() = _mapPingleListState.asSharedFlow()

    private val _pingleParticipationState = MutableSharedFlow<UiState<Long>>()
    val pingleParticipationState get() = _pingleParticipationState.asSharedFlow()

    private val _pingleDeleteState = MutableSharedFlow<UiState<Unit?>>()
    val pingleDeleteState get() = _pingleDeleteState.asSharedFlow()

    private val _mainListOrderType = MutableStateFlow(MainListOrderType.NEW)
    val mainListOrderType get() = _mainListOrderType.asStateFlow()

    private val _mainListPingleListState = MutableSharedFlow<UiState<List<MainListPingleModel>>>()
    val mainListPingleListState get() = _mainListPingleListState.asSharedFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }

    fun clearCategory() {
        _category.value = null
    }

    fun setHomeViewType(homeViewType: HomeViewType) {
        _homeViewType.value = homeViewType
    }

    fun setSearchWord(searchWord: String?) {
        _searchWord.value = searchWord
    }

    fun clearSearchWord() {
        _searchWord.value = null
        _lastSearchWord = null
    }

    fun setLastSearchWord(searchWord: String?) {
        _lastSearchWord = searchWord
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

    fun initMapPingleListState() {
        viewModelScope.launch {
            _mapPingleListState.emit(UiState.Empty)
        }
    }

    fun setMainListOrderType(mainListOrderType: MainListOrderType) {
        _mainListOrderType.value = mainListOrderType
    }

    fun getGroupName(): String = localStorage.groupName

    fun deletePingleCancel(meetingId: Long) {
        viewModelScope.launch {
            _pingleParticipationState.emit(UiState.Loading)
            deletePingleCancelUseCase(
                meetingId = meetingId
            ).onSuccess {
                _pingleParticipationState.emit(UiState.Success(meetingId))
            }.onFailure { exception: Throwable ->
                _pingleParticipationState.emit(
                    UiState.Error(
                        message = if (exception is HttpException) {
                            exception.response()?.errorBody()
                                ?.byteString()?.utf8()?.let { errorBodyJson ->
                                    Json.decodeFromString<NullableBaseResponse<Unit>>(errorBodyJson).message
                                }
                        } else {
                            exception.message
                        },
                        code = (exception as? HttpException)?.response()?.code()
                    )
                )
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

    fun getMainListPingleList() {
        viewModelScope.launch {
            _mainListPingleListState.emit(UiState.Loading)
            if (_searchWord.value?.isBlank() == true) {
                _mainListPingleListState.emit(UiState.Success(emptyList()))
            } else {
                getMainListPingleListUseCase(
                    searchWord = _searchWord.value,
                    category = _category.value?.name,
                    teamId = localStorage.groupId.toLong(),
                    order = _mainListOrderType.value.name
                ).onSuccess { mainListPingleList ->
                    _mainListPingleListState.emit(UiState.Success(mainListPingleList.map { pingleEntity -> pingleEntity.toMainListPingleModel() }))
                }.onFailure { throwable ->
                    _mainListPingleListState.emit(UiState.Error(throwable.message))
                }
            }
        }
    }

    fun getMapPingleList(pinId: Long) {
        viewModelScope.launch {
            _mapPingleListState.emit(UiState.Loading)
            runCatching {
                getMapPingleListUseCase(
                    teamId = localStorage.groupId.toLong(),
                    pinId = pinId,
                    category = _category.value?.name
                ).collect() { mapPingleList ->
                    _mapPingleListState.emit(UiState.Success(Pair(pinId, mapPingleList)))
                }
            }.onFailure { exception ->
                _mapPingleListState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun getPinListWithoutFilter(isSearching: Boolean = false) {
        viewModelScope.launch {
            _pinEntityListState.value = UiState.Loading
            if (_searchWord.value?.isBlank() == true) {
                _pinEntityListState.emit(UiState.Success(Pair(isSearching, emptyList())))
            } else {
                runCatching {
                    getPinListWithoutFilteringUseCase(
                        teamId = localStorage.groupId.toLong(),
                        category = _category.value?.name,
                        searchWord = _searchWord.value
                    ).collect() { pinList ->
                        _pinEntityListState.value = UiState.Success(Pair(isSearching, pinList))
                    }
                }.onFailure { exception: Throwable ->
                    _pinEntityListState.value = UiState.Error(exception.message)
                }
            }
        }
    }

    fun postPingleJoin(meetingId: Long) {
        viewModelScope.launch {
            _pingleParticipationState.emit(UiState.Loading)
            postPingleJoinUseCase(
                meetingId = meetingId
            ).onSuccess {
                _pingleParticipationState.emit(UiState.Success(meetingId))
            }.onFailure { exception: Throwable ->
                _pingleParticipationState.emit(
                    UiState.Error(
                        message = if (exception is HttpException) {
                            exception.response()?.errorBody()
                                ?.byteString()?.utf8()?.let { errorBodyJson ->
                                    Json.decodeFromString<NullableBaseResponse<Unit>>(errorBodyJson).message
                                }
                        } else {
                            exception.message
                        },
                        code = (exception as? HttpException)?.response()?.code(),
                        data = meetingId
                    )
                )
            }
        }
    }

    companion object {
        private const val GROUP_ID = "GroupId"
        const val DEFAULT_SELECTED_MARKER_POSITION = -1
    }
}
