package org.sopt.pingle.presentation.ui.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.UserEntity
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
import org.sopt.pingle.domain.usecase.SetDummyDataUseCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val setDummyDataUseCase: SetDummyDataUseCase,
    private val getDummyUserListUseCase: GetDummyUserListUseCase
) : ViewModel() {
    private val _dummyUserListState = MutableStateFlow<UiState<List<UserEntity>>>(UiState.Empty)
    val dummyUserState = _dummyUserListState.asStateFlow()

    fun setDummyData() {
        setDummyDataUseCase(DUMMY)
    }

    fun getDummyUserList(page: Int) {
        viewModelScope.launch {
            _dummyUserListState.value = UiState.Loading
            runCatching {
                getDummyUserListUseCase(page).collect() { dummyUserList ->
                    _dummyUserListState.value = UiState.Success(dummyUserList)
                }
            }.onFailure { exception: Throwable ->
                _dummyUserListState.value = UiState.Error(exception.message)
            }
        }
    }

    companion object {
        const val PAGE = 2
        const val DUMMY = 3
    }
}
