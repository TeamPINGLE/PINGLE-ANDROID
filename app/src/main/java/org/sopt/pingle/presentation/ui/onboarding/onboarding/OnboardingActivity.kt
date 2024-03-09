package org.sopt.pingle.presentation.ui.onboarding.onboarding

import android.content.Intent
import android.os.Bundle
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityOnboardingBinding
import org.sopt.pingle.presentation.ui.joingroup.JoinGroupSearchActivity
import org.sopt.pingle.presentation.ui.newgroup.NewGroupActivity
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.activity.setDoubleBackPressToExit
import org.sopt.pingle.util.base.BindingActivity

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
            AmplitudeUtils.trackEventWithProperty(CLICK_METHOD_OPTION, OPTION, EXISTING_GROUP)
        }

        binding.includeOnboardingGroupNew.root.setOnClickListener {
            navigateToNewGroup()
            AmplitudeUtils.trackEventWithProperty(CLICK_METHOD_OPTION, OPTION, CREATE_GROUP)
        }
    }

    private fun navigateToJoinGroupSearch() {
        Intent(this, JoinGroupSearchActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToNewGroup() {
        Intent(this, NewGroupActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        const val CLICK_METHOD_OPTION = "click_method_option"
        const val OPTION = "option"
        const val EXISTING_GROUP = "existing_group"
        const val CREATE_GROUP = "create_group"
    }
}
