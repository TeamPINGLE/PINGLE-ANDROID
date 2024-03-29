package org.sopt.pingle.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.data.service.KakaoAuthService
import org.sopt.pingle.databinding.ActivityAuthBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.main.more.MoreFragment.Companion.MORE_FRAGMENT
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity.Companion.FROM_ACTIVITY
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.activity.setDoubleBackPressToExit
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.view.UiState
import org.sopt.pingle.util.view.setOnSingleClickListener
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : BindingActivity<ActivityAuthBinding>(R.layout.activity_auth) {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        when (intent.getStringExtra(FROM_ACTIVITY)) {
            MORE_FRAGMENT -> setDoubleBackPressToExit(binding.root)
            else -> Unit
        }
    }

    private fun addListeners() {
        binding.btnAuthKakao.setOnSingleClickListener {
            kakaoAuthService.loginKakao(viewModel::login, viewModel::saveAccount)
            AmplitudeUtils.trackEventWithProperty(START_SIGNUP, SIGNUP_TYPE, KAKAO)
        }
    }

    private fun collectData() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    viewModel.getUserInfo()
                    AmplitudeUtils.trackEvent(COMPLETE_SIGNUP)
                }

                is UiState.Error -> Timber.tag(TAG).d(KAKAO_LOGIN_ERROR + "${uiState.message}")
                is UiState.Loading -> Timber.tag(TAG).d(KAKAO_LOGIN_LOADING)
                is UiState.Empty -> Timber.tag(TAG).d(KAKAO_LOGIN_EMPTY)
            }
        }.launchIn(lifecycleScope)

        viewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> if (uiState.data.groups.isEmpty()) navigateToOnBoarding() else navigateToMain()
                is UiState.Error -> Timber.tag(TAG).d(USER_INFO_ERROR + uiState.message)
                is UiState.Loading -> Timber.tag(TAG).d(USER_INFO_LOADING)
                is UiState.Empty -> Timber.tag(TAG).d(USER_INFO_EMPTY)
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToOnBoarding() {
        Intent(this, OnboardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    companion object {
        const val TAG = "AuthActivity"
        const val KAKAO_LOGIN_LOADING = "Kakao Login Loading..."
        const val KAKAO_LOGIN_EMPTY = "Kakao Login Empty"
        const val KAKAO_LOGIN_ERROR = "Kakao Login Error : "
        const val USER_INFO_LOADING = "User Info Loading..."
        const val USER_INFO_EMPTY = "User Info Empty"
        const val USER_INFO_ERROR = "User Info Error : "
        private const val START_SIGNUP = "start_signup"
        private const val SIGNUP_TYPE = "signup type"
        private const val KAKAO = "kakao"
        private const val COMPLETE_SIGNUP = "complete_signup"
    }
}
