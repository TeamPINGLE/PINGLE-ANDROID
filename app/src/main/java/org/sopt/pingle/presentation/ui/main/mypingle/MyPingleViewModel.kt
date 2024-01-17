package org.sopt.pingle.presentation.ui.main.mypingle

import androidx.lifecycle.MutableLiveData
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
import org.sopt.pingle.domain.usecase.GetPingleParticipationListUseCase
import org.sopt.pingle.domain.usecase.PostPingleCancelUseCase
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getPingleParticipationListUseCase: GetPingleParticipationListUseCase,
    private val postPingleCancelUseCase: PostPingleCancelUseCase
) : ViewModel() {
    private val _myPingleState = MutableSharedFlow<UiState<List<MyPingleEntity>>>()
    val myPingleState get() = _myPingleState.asSharedFlow()

    private var _isParticipation = MutableLiveData<Boolean>(false)
    val isParticipation get() = _isParticipation

    private var _myPingleCancelState = MutableStateFlow<UiState<Unit?>>(UiState.Empty)
    val myPingleCancelState get() = _myPingleCancelState.asStateFlow()

    fun getPingleParticipationList(participation: Boolean) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                getPingleParticipationListUseCase(
                    teamId = localStorage.groupId,
                    participation = participation
                ).collect { myPingleEntity ->
                    if (myPingleEntity.isEmpty()) {
                        _myPingleState.emit(UiState.Empty)
                    } else {
                        _myPingleState.emit(UiState.Success(myPingleEntity))
                    }
                }
            }.onFailure {
                _myPingleState.emit(UiState.Error(it.message))
            }
        }
    }

    fun riversParticipation() = (!_isParticipation.value!!)

    fun postPingleCancel(meetingId: Long) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                postPingleCancelUseCase(meetingId = meetingId).collect { data ->
                    _myPingleCancelState.emit(UiState.Success(data))
                }
            }.onFailure {
                _myPingleCancelState.emit(UiState.Error(it.message))
            }
        }
    }
}
