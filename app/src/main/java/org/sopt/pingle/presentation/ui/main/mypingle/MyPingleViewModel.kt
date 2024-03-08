package org.sopt.pingle.presentation.ui.main.mypingle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.usecase.DeletePingleCancelUseCase
import org.sopt.pingle.domain.usecase.DeletePingleDeleteUseCase
import org.sopt.pingle.domain.usecase.GetMyPingleListUseCase
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.util.view.UiState
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getMyPingleListUseCase: GetMyPingleListUseCase,
    private val deletePingleCancelUseCase: DeletePingleCancelUseCase,
    private val deletePingleDeleteUseCase: DeletePingleDeleteUseCase
) : ViewModel() {
    private val _myPingleType = MutableStateFlow(MyPingleType.SOON)
    val myPingleType get() = _myPingleType.asStateFlow()

    private val _myPingleListState = MutableSharedFlow<UiState<List<MyPingleEntity>>>()
    val myPingleListState get() = _myPingleListState.asSharedFlow()

    private var _myPingleState = MutableStateFlow<UiState<Unit?>>(UiState.Empty)
    val myPingleState get() = _myPingleState.asStateFlow()

    private val _selectedMyPingleData = MutableStateFlow<MyPingleEntity?>(null)

    private val _myPingleList = MutableStateFlow<List<MyPingleEntity>>(emptyList())

    private var oldPosition = DEFAULT_OLD_POSITION

    fun setMyPingleType(myPingleType: MyPingleType) {
        _myPingleType.value = myPingleType
    }

    fun updateMyPingleList(newPosition: Int) {
        when {
            oldPosition == DEFAULT_OLD_POSITION -> setIsSelected(newPosition)

            oldPosition == newPosition -> {
                setIsSelected(newPosition)
                oldPosition = DEFAULT_OLD_POSITION
            }

            oldPosition >= _myPingleList.value.size -> {
                oldPosition = DEFAULT_OLD_POSITION
                setIsSelected(newPosition)
            }

            else -> {
                if (getIsSelected(oldPosition)) setIsSelected(oldPosition)
                setIsSelected(newPosition)
            }
        }
        _selectedMyPingleData.value =
            if (getIsSelected(newPosition)) _myPingleList.value[newPosition] else null
        oldPosition = newPosition
    }

    fun clearMyPingleListSelection() {
        if (oldPosition != DEFAULT_OLD_POSITION && getIsSelected(oldPosition)) {
            setIsSelected(oldPosition)
        }
        oldPosition = DEFAULT_OLD_POSITION
    }

    private fun setIsSelected(position: Int) {
        _myPingleList.value[position].isSelected.set(
            !_myPingleList.value[position].isSelected.get()
        )
    }

    private fun getIsSelected(position: Int) = _myPingleList.value[position].isSelected.get()

    fun getPingleParticipationList() {
        viewModelScope.launch {
            _myPingleListState.emit(UiState.Loading)
            _myPingleList.value = emptyList()
            _selectedMyPingleData.value = null
            runCatching {
                getMyPingleListUseCase(
                    teamId = localStorage.groupId,
                    participation = _myPingleType.value.boolean
                ).collect { myPingleEntity ->
                    if (myPingleEntity.isEmpty()) {
                        _myPingleListState.emit(UiState.Empty)
                    } else {
                        _myPingleList.value = myPingleEntity
                        _myPingleListState.emit(UiState.Success(myPingleEntity))
                    }
                }
            }.onFailure { exception: Throwable ->
                _myPingleListState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun deletePingleCancel(meetingId: Long) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            deletePingleCancelUseCase(meetingId = meetingId).onSuccess { data ->
                _myPingleState.emit(UiState.Success(data))
            }.onFailure { exception: Throwable ->
                _myPingleState.emit(
                    UiState.Error(
                        message = (exception as? HttpException)?.response()?.message()
                            ?: exception.message,
                        code = (exception as? HttpException)?.response()?.code()
                    )
                )
            }
        }
    }

    fun deletePingleDelete(meetingId: Long) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                deletePingleDeleteUseCase(meetingId = meetingId).collect() { data ->
                    _myPingleState.emit(UiState.Success(data))
                }
            }.onFailure { exception: Throwable ->
                _myPingleState.emit(UiState.Error(exception.message))
            }
        }
    }

    companion object {
        private const val DEFAULT_OLD_POSITION = -1
    }
}
