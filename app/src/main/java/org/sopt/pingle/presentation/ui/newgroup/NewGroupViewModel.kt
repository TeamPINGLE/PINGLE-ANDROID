package org.sopt.pingle.presentation.ui.newgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity
import org.sopt.pingle.domain.usecase.GetNewGroupCheckNameUseCase
import org.sopt.pingle.domain.usecase.GetNewGroupKeywordsUserCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val getNewGroupCheckNameUseCase: GetNewGroupCheckNameUseCase,
    private val getNewGroupKeywordsUserCase: GetNewGroupKeywordsUserCase
) : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _newGroupCheckNameState =
        MutableStateFlow<UiState<NewGroupCheckNameEntity>>(UiState.Empty)
    val newGroupCheckNameState get() = _newGroupCheckNameState.asStateFlow()

    val newGroupTeamName = MutableStateFlow<String>("")

    private val _newGroupKeywordsState =
        MutableStateFlow<UiState<List<NewGroupKeywordsEntity>>>(UiState.Empty)
    val newGroupKeywordsState get() = _newGroupKeywordsState.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun getNewGroupCheckName(teamName: String) {
        viewModelScope.launch {
            _newGroupCheckNameState.emit(UiState.Loading)
            getNewGroupCheckNameUseCase(teamName = teamName).onSuccess { newGroupCheckName ->
                _newGroupCheckNameState.value = UiState.Success(newGroupCheckName)
            }.onFailure { throwable ->
                _newGroupCheckNameState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun getNewGroupKeywords() {
        viewModelScope.launch {
            _newGroupKeywordsState.emit(UiState.Loading)
            getNewGroupKeywordsUserCase().onSuccess { newGroupKeywords ->
                _newGroupKeywordsState.value = UiState.Success(newGroupKeywords)
            }.onFailure { throwable ->
                _newGroupKeywordsState.value = UiState.Error(throwable.message)
            }
        }
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
