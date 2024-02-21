package org.sopt.pingle.presentation.ui.onboarding

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

        if (pingleIndex != INVALID_INDEX) {
            binding.tvOnboardingExplanationTitle.text = SpannableString(titleText).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(itemView.context, R.color.pingle_green)
                    ),
                    pingleIndex,
                    pingleIndex + PINGLE.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } else {
            binding.tvOnboardingExplanationTitle.text = titleText
        }

        /*binding.tvOnboardingExplanationTitle.text = SpannableString(
            getString(itemView.context, R.string.on_boarding_explanation_logo_title)
        ).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(itemView.context, R.color.pingle_green)
                ),
                PINGLE_START,
                PINGLE_END,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }*/
    }

    companion object {
        private const val PINGLE_START = 7
        private const val PINGLE_END = 14
        private const val PINGLE = "PINGLE"
        private const val INVALID_INDEX = -1
    }
}
