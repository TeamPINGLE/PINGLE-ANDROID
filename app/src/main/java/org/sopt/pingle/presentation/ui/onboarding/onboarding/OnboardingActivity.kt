package org.sopt.pingle.presentation.ui.onboarding.onboarding

import android.content.Intent
import android.os.Bundle
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityOnboardingBinding
import org.sopt.pingle.presentation.ui.joingroup.JoinGroupSearchActivity
import org.sopt.pingle.util.activity.setDoubleBackPressToExit
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.navigateToWebView

class OnboardingActivity :
    BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        setDoubleBackPressToExit(binding.root)
    }

    private fun addListeners() {
        binding.includeOnBoardingGroupOriginal.root.setOnClickListener {
            navigateToJoinGroupSearch()
        }

        binding.includeOnboardingGroupNew.root.setOnClickListener {
            startActivity(navigateToWebView(NEW_GROUP_LINK))
        }
    }

    private fun navigateToJoinGroupSearch() {
        Intent(this, JoinGroupSearchActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        const val NEW_GROUP_LINK =
            "https://docs.google.com/forms/d/10WxvEzSVRrRvRGXsYf9Z5oXv4HsNuAwG2QicB4bY0aY/edit"
    }
}
