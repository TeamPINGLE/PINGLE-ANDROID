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
import org.sopt.pingle.presentation.ui.onboarding.OnBoardingActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : BindingActivity<ActivityAuthBinding>(R.layout.activity_auth) {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.btnAuthKakao.setOnClickListener {
            kakaoAuthService.loginKakao(viewModel::login, viewModel::saveAccount)
        }
    }

    private fun collectData() {
        viewModel.loginUiState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Loading -> Timber.d(KAKAO_LOGIN_LADING)

                is UiState.Empty -> Timber.d(KAKAO_LOGIN_EMPTY)

                is UiState.Success -> navigateToOnBoarding()

                is UiState.Error -> Timber.d(KAKAO_LOGIN_ERROR + "${uiState.message}")
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToOnBoarding() {
        Intent(this, OnBoardingActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        const val KAKAO_LOGIN_LADING = "Kakao Login Lading..."
        const val KAKAO_LOGIN_EMPTY = "Kakao Login Empty"
        const val KAKAO_LOGIN_ERROR = "Kakao Login Error : "
    }
}
