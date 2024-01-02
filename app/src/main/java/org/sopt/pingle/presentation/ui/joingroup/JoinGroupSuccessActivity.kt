package org.sopt.pingle.presentation.ui.joingroup

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupSuccessBinding
import org.sopt.pingle.util.base.BindingActivity

class JoinGroupSuccessActivity :
    BindingActivity<ActivityJoinGroupSuccessBinding>(R.layout.activity_join_group_success) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDescriptionTextStyle()
    }

    private fun setDescriptionTextStyle() {
        val spannableText = SpannableString(binding.tvJoinGroupSuccessDescription.text)

        spannableText.setSpan(
            TextAppearanceSpan(
                this@JoinGroupSuccessActivity,
                R.style.TextAppearance_Pingle_Sub_Semi_16
            ),
            START,
            END,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableText.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@JoinGroupSuccessActivity,
                    R.color.g_01
                )
            ),
            START,
            END,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvJoinGroupSuccessDescription.text = spannableText
    }

    companion object {
        const val START = 0
        const val END = 3
    }
}
