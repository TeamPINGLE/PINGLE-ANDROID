package org.sopt.pingle.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityOnboardingExplanationBinding
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.util.base.BindingActivity

class OnboardingExplanationActivity :
    BindingActivity<ActivityOnboardingExplanationBinding>(R.layout.activity_onboarding_explanation) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
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

    private fun navigateToAuth() {
        Intent(this, AuthActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
