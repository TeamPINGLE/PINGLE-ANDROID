package org.sopt.pingle.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayoutMediator
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityOnboardingExplanationBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.util.activity.FINISH_INTERVAL_TIME
import org.sopt.pingle.util.activity.INIT_BACK_PRESSED_TIME
import org.sopt.pingle.util.activity.SNACKBAR_BOTTOM_MARGIN
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf

class OnboardingExplanationActivity :
    BindingActivity<ActivityOnboardingExplanationBinding>(R.layout.activity_onboarding_explanation) {

    private lateinit var onBackPressed: OnBackPressedCallback
    private var backPressedTime = INIT_BACK_PRESSED_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        onBackPressedBtn()
    }

    private fun initLayout() {
        val adapter = OnboardingExplanationAdapter(this)
        binding.vpOnboardingExplanation.adapter = adapter
        TabLayoutMediator(
            binding.tlOnboardingIndicator,
            binding.vpOnboardingExplanation,
        ) { _, _ -> }.attach()
    }

    private fun addListeners() {
        with(binding) {
            btnOnboardingExplanationNext.setOnClickListener {
                if (vpOnboardingExplanation.currentItem == OnboardingExplanationAdapter.ONBOARDING_SIZE - OnboardingExplanationAdapter.POSITION_MINUS) {
                    navigateToAuth()
                } else {
                    vpOnboardingExplanation.currentItem++
                }
            }
            tvOnboardingExplanationSkip.let {
                it.bringToFront()
                it.setOnClickListener { navigateToAuth() }
            }
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousPageOrExit()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    private fun navigateToAuth() {
        Intent(this, AuthActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToPreviousPageOrExit() {
        with(binding) {
            if (vpOnboardingExplanation.currentItem == OnboardingExplanationAdapter.DEFAULT_POSITION) {
                if (System.currentTimeMillis() - backPressedTime <= FINISH_INTERVAL_TIME) {
                    finish()
                } else {
                    backPressedTime = System.currentTimeMillis()
                    PingleSnackbar.makeSnackbar(
                        view = binding.root,
                        message = stringOf(R.string.all_on_back_pressed_snackbar),
                        bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                        snackbarType = SnackbarType.GUIDE,
                    )
                }
            } else {
                vpOnboardingExplanation.currentItem--
            }
        }
    }
}
