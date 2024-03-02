package org.sopt.pingle.presentation.ui.newgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity
import org.sopt.pingle.domain.repository.NewGroupRepository
import org.sopt.pingle.domain.usecase.GetNewGroupKeywordsUserCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val newGroupRepository: NewGroupRepository,
    private val getNewGroupKeywordsUserCase: GetNewGroupKeywordsUserCase
) : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _newGroupKeywords =
        MutableStateFlow<UiState<List<NewGroupKeywordsEntity>>>(UiState.Empty)
    val newGroupKeywords get() = _newGroupKeywords.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun getNewGroupKeywords() {
        viewModelScope.launch {
            _newGroupKeywords.emit(UiState.Loading)
            getNewGroupKeywordsUserCase().onSuccess { newGroupKeywords ->
                _newGroupKeywords.value = UiState.Success(newGroupKeywords)
            }.onFailure { throwable ->
                _newGroupKeywords.value = UiState.Error(throwable.message)
            }
        }
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
