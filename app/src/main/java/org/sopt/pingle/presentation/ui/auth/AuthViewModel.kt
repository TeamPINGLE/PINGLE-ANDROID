package org.sopt.pingle.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.data.model.remote.request.RequestAuthDto
import org.sopt.pingle.domain.model.UserInfoEntity
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.usecase.GetUserInfoUseCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: PingleLocalDataSource,
    private val getUserInfoUserCase: GetUserInfoUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val loginState get() = _loginState.asStateFlow()

    private val _userInfoState = MutableStateFlow<UiState<UserInfoEntity>>(UiState.Empty)
    val userInfoState get() = _userInfoState.asStateFlow()

    fun login(kakaoAccessToken: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            authRepository.postLogin(
                kakaoAccessToken,
                RequestAuthDto(LOGIN_PLATFORM)
            ).onSuccess { authEntitiy ->
                with(localStorage) {
                    isLogin = true
                    accessToken = (HEADER_BEARER + authEntitiy.accessToken)
                    refreshToken = (HEADER_BEARER + authEntitiy.refreshToken)
                }
                _loginState.value = UiState.Success(Unit)
            }.onFailure {
                _loginState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(userName: String) {
        localStorage.userName = userName
    }

    fun isLocalToken(): Boolean = (localStorage.accessToken != "")

    fun isLocalGroupId(): Boolean = localStorage.groupId > 0

    fun getUserInfo() {
        _userInfoState.value = UiState.Loading
        viewModelScope.launch {
            runCatching {
                getUserInfoUserCase().collect { userInfoEntity ->
                    _userInfoState.value = UiState.Success(userInfoEntity)
                    with(localStorage) {
                        userName = userInfoEntity.name
                        if (userInfoEntity.groups.isEmpty()) {
                            groupId = -1
                            groupName = ""
                        } else {
                            groupId = userInfoEntity.groups[FIRST_INDEX].id
                            groupName = userInfoEntity.groups[FIRST_INDEX].name
                        }
                    }
                }
            }.onFailure {
                _userInfoState.value = UiState.Error(it.message)
            }
        }
    }

    companion object {
        const val LOGIN_PLATFORM = "KAKAO"
        const val HEADER_BEARER = "Bearer "
        const val FIRST_INDEX = 0
    }
}
