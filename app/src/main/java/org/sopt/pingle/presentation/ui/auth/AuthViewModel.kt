package org.sopt.pingle.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.AuthEntity
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: PingleDataSource
) : ViewModel() {
    private val _loginUiState = MutableStateFlow<UiState<AuthEntity>>(UiState.Empty)
    val loginUiState get() = _loginUiState.asStateFlow()

    fun login(accessToken: String) {
        _loginUiState.value = UiState.Loading
        viewModelScope.launch {
            authRepository.postLogin(
                accessToken,
                RequestAuthDto(LOGIN_PLATFORM)
            ).onSuccess {
                _loginUiState.value = UiState.Success(AuthEntity(it.accessToken, it.refreshToken))
            }.onFailure {
                _loginUiState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(userName: String) {
        localStorage.userName = userName
    }

    companion object {
        const val LOGIN_PLATFORM = "KAKAO"
    }
}
