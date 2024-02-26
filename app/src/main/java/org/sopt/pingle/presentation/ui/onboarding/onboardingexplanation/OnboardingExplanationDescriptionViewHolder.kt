package org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemOnboardingExplanationDescriptionBinding
import org.sopt.pingle.presentation.type.OnboardingExplanationType

class OnboardingExplanationDescriptionViewHolder(
    private val binding: ItemOnboardingExplanationDescriptionBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(onboarding: OnboardingExplanationType) {
        binding.page = onboarding
    }
}
