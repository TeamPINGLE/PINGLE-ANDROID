package org.sopt.pingle.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.pingle.R
import org.sopt.pingle.data.datasourceimpl.local.PingleDataSourceImpl
import org.sopt.pingle.databinding.ActivitySplashBinding
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.util.base.BindingActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY_TIME)
            navigateToOnBoarding()
        }
    }

    private fun navigateToOnBoarding() {
        val storage = PingleDataSourceImpl(this)

        val nextActivity =
            if (storage.isLogin) MainActivity::class.java else AuthActivity::class.java

        startActivity(Intent(this, nextActivity))
        finish()
    }

    companion object {
        const val SPLASH_SCREEN_DELAY_TIME = 1500L
    }
}
