package org.sopt.pingle.presentation.ui.main.mypingle

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.usecase.DeletePingleCancelUseCase
import org.sopt.pingle.domain.usecase.GetMyPingleListUseCase
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getMyPingleListUseCase: GetMyPingleListUseCase,
    private val deletePingleCancelUseCase: DeletePingleCancelUseCase
) : ViewModel() {
    private val _myPingleType = MutableStateFlow(MyPingleType.SOON)
    val myPingleType get() = _myPingleType.asStateFlow()

    private val _myPingleState = MutableSharedFlow<UiState<List<MyPingleEntity>>>()
    val myPingleState get() = _myPingleState.asSharedFlow()

    private var _myPingleCancelState = MutableStateFlow<UiState<Unit?>>(UiState.Empty)
    val myPingleCancelState get() = _myPingleCancelState.asStateFlow()

    private val _selectedMyPingleData = MutableStateFlow<MyPingleEntity?>(null)

    private val _myPingleList = MutableStateFlow<List<MyPingleEntity>>(emptyList())

    private var oldPosition = DEFAULT_OLD_POSITION

    fun setMyPingleType(myPingleType: MyPingleType) {
        _myPingleType.value = myPingleType
    }

    fun getPingleParticipationList() {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            _myPingleList.value = emptyList()
            _selectedMyPingleData.value = null
            runCatching {
                getMyPingleListUseCase(
                    teamId = localStorage.groupId,
                    participation = _myPingleType.value.boolean
                ).collect { myPingleEntity ->
                    if (myPingleEntity.isEmpty()) {
                        _myPingleState.emit(UiState.Empty)
                    } else {
                        _myPingleList.value = myPingleEntity
                        _myPingleState.emit(UiState.Success(myPingleEntity))
                    }
                }
            }.onFailure {
                _myPingleState.emit(UiState.Error(it.message))
            }
        }
    }

    fun deletePingleCancel(meetingId: Long) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                deletePingleCancelUseCase(meetingId = meetingId).collect { data ->
                    _myPingleCancelState.emit(UiState.Success(data))
                }
            }.onFailure {
                _myPingleCancelState.emit(UiState.Error(it.message))
            }
        }
    }

    fun updateMyPingleList(newPosition: Int) {
        when (oldPosition) {
            DEFAULT_OLD_POSITION -> setIsSelected(newPosition)

            newPosition -> {
                setIsSelected(newPosition)
                oldPosition = DEFAULT_OLD_POSITION
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

    companion object {
        private const val DEFAULT_OLD_POSITION = -1
    }
}
