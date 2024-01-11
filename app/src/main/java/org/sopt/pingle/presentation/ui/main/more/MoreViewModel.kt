package org.sopt.pingle.presentation.ui.main.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val pingleLocalDataSource: PingleLocalDataSource
) : ViewModel() {
    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val logoutState get() = _logoutState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
                .onSuccess { code ->
                    if (code == SUCCESS_CODE) {
                        _logoutState.value = UiState.Success(true)
                        pingleLocalDataSource.clear()
                    } else {
                        _logoutState.value = UiState.Error(null)
                    }
                }.onFailure { throwable ->
                    _logoutState.value = UiState.Error(throwable.message)
                    Timber.e(throwable.message)
                }
        }
    }

    companion object {
        const val SUCCESS_CODE = 200
    }
}
