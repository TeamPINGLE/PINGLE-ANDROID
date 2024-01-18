package org.sopt.pingle.presentation.ui.main.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.UserInfoEntity
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.usecase.GetUserInfoUseCase
import org.sopt.pingle.util.view.UiState
import retrofit2.HttpException

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val localStorage: PingleLocalDataSource,
    private val authRepository: AuthRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _logoutState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val logoutState get() = _logoutState.asStateFlow()

    private val _withDrawState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val withDrawState get() = _withDrawState.asStateFlow()

    private val _userInfoState = MutableStateFlow<UiState<UserInfoEntity>>(UiState.Empty)
    val userInfoState get() = _userInfoState.asStateFlow()

    fun getGroupName(): String = localStorage.groupName

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
                .onSuccess { code ->
                    if (code == SUCCESS_CODE) {
                        _logoutState.value = UiState.Success(true)
                        localStorage.clear()
                    } else {
                        _logoutState.value = UiState.Error(null)
                    }
                }.onFailure { throwable ->
                    _logoutState.value = UiState.Error(throwable.message)
                }
        }
    }

    fun withDraw() {
        viewModelScope.launch {
            authRepository.withDraw()
                .onSuccess { code ->
                    if (code == SUCCESS_CODE) {
                        _withDrawState.value = UiState.Success(true)
                        localStorage.clear()
                    } else {
                        _withDrawState.value = UiState.Error(null)
                    }
                }.onFailure { throwable ->
                    _withDrawState.value = UiState.Error(
                        if (throwable is HttpException) {
                            throwable.response()?.code().toString()
                        } else {
                            throwable.message
                        }
                    )
                }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UiState.Loading
            runCatching {
                getUserInfoUseCase().collect() { userInfo ->
                    _userInfoState.value = UiState.Success(userInfo)
                }
            }.onFailure { exception: Throwable ->
                _userInfoState.value = UiState.Error(exception.message)
            }
        }
    }

    companion object {
        const val SUCCESS_CODE = 200
    }
}
