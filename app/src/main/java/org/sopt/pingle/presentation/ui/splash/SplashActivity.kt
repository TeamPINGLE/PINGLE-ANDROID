package org.sopt.pingle.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivitySplashBinding
import org.sopt.pingle.presentation.ui.auth.AuthViewModel
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity
import org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation.OnboardingExplanationActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
        collectData()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME)
            if (authViewModel.isLocalToken()) {
                if (authViewModel.isLocalGroupId()) navigateToMain() else authViewModel.getUserInfo()
            } else {
                navigateToOnboardingExplanation()
            }
        }
    }

    private fun collectData() {
        authViewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> if (uiState.data.groups.isEmpty()) navigateToOnboarding() else navigateToMain()
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun navigateToOnboarding() {
        Intent(this, OnboardingActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun navigateToOnboardingExplanation() {
        Intent(this, OnboardingExplanationActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    companion object {
        const val SPLASH_SCREEN_DELAY_TIME = 1500L
    }
}
