package org.sopt.pingle.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.pingle.R
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.databinding.ActivitySplashBinding
import org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation.OnboardingExplanationActivity
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    @Inject
    lateinit var localStorage: PingleLocalDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME)
            navigateToAuth()
        }
    }

    private fun navigateToAuth() {
        Intent(this, OnboardingExplanationActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    companion object {
        const val SPLASH_SCREEN_DELAY_TIME = 1500L
    }
}
