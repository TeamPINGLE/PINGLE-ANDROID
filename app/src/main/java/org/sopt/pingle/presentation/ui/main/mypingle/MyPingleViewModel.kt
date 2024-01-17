package org.sopt.pingle.presentation.ui.main.mypingle

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
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getMyPingleListUseCase: GetMyPingleListUseCase,
    private val deletePingleCancelUseCase: DeletePingleCancelUseCase
) : ViewModel() {
    private val _myPingleState = MutableSharedFlow<UiState<List<MyPingleEntity>>>()
    val myPingleState get() = _myPingleState.asSharedFlow()

    private var _myPingleCancelState = MutableStateFlow<UiState<Unit?>>(UiState.Empty)
    val myPingleCancelState get() = _myPingleCancelState.asStateFlow()

    fun getPingleParticipationList(participation: Boolean) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                getMyPingleListUseCase(
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
}
