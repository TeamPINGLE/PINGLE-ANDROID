package org.sopt.pingle.presentation.ui.main.mypingle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.usecase.GetPingleParticipationList
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class MyPingleViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val getPingleParticipationList: GetPingleParticipationList
) : ViewModel() {
    private val _myPingleState = MutableSharedFlow<UiState<List<MyPingleEntity>>>()
    val myPingleState get() = _myPingleState.asSharedFlow()

    fun pingleParticipationList(participation: Boolean) {
        viewModelScope.launch {
            _myPingleState.emit(UiState.Loading)
            runCatching {
                getPingleParticipationList(
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
}
