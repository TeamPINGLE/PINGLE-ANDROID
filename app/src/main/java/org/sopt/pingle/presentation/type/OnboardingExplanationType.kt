package org.sopt.pingle.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.pingle.R

enum class OnboardingExplanationType(@StringRes val description: Int, @DrawableRes val image: Int) {
    // For the FIRST page, check layout > item_onboarding_expansion_tile.xml
    SECOND(
        R.string.on_boarding_explanation_group_description,
        R.drawable.img_onboarding_explanation_group
    ),
    THIRD(
        R.string.on_boarding_explanation_pingle_description,
        R.drawable.img_onboarding_explanation_pingle
    ),
    FOURTH(
        R.string.on_boarding_explanation_category_description,
        R.drawable.img_onboarding_explanation_category
    )
}
