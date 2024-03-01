package org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemOnboardingExplanationTitleBinding

class OnboardingExplanationTitleViewHolder(
    private val binding: ItemOnboardingExplanationTitleBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind() {
        val titleText = getString(itemView.context, R.string.on_boarding_explanation_logo_title)
        val pingleIndex = titleText.indexOf(PINGLE)

        binding.tvOnboardingExplanationTitle.text = titleText.takeIf { pingleIndex != INVALID_INDEX }?.let {
            SpannableString(it).apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.pingle_green)),
                    pingleIndex,
                    pingleIndex + PINGLE.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } ?: titleText
    }

    companion object {
        private const val PINGLE = "PINGLE"
        private const val INVALID_INDEX = -1
    }
}
