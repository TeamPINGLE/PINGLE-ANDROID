package org.sopt.pingle.presentation.ui.participant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.ParticipantEntity
import org.sopt.pingle.domain.usecase.GetParticipantListUseCase
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class ParticipantViewModel @Inject constructor(
    private val getParticipantListUseCase: GetParticipantListUseCase,
) : ViewModel() {
    private val _participantListState = 
        MutableSharedFlow<UiState<ParticipantEntity>>()
    val participantListState get() = _participantListState.asSharedFlow()

    fun getParticipantList(meetingId: Long) {
        viewModelScope.launch {
            _participantListState.emit(UiState.Loading)
            runCatching {
                getParticipantListUseCase(meetingId).collect() { participantList ->
                    when(participantList == null) {
                        true -> {
                            _participantListState.emit(UiState.Empty)
                        }
                        false -> {
                            _participantListState.emit(UiState.Success(participantList))
                        }
                    }
                }
            }.onFailure { exception: Throwable ->
                _participantListState.emit(UiState.Error(exception.message))
            }
        }
    }
}
