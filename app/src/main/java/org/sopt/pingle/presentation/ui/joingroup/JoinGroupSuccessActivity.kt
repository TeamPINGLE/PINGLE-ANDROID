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

        initLayout()
    }

    private fun initLayout() {
        setDescriptionTextStyle(countGroupName())
    }

    private fun getGroupName(): String {
        // TODO 이전 화면에서 Intent를 통해서 groupName을 가져옴

        return "SOPT"
    }

    private fun countGroupName() = getGroupName().length

    private fun setDescription() {
        binding.tvJoinGroupSuccessDescriptionGroupName.text =
            getString(
                R.string.join_group_success_description_group_name,
                getGroupName()
            )
    }

    private fun setDescriptionTextStyle(groupNameCount: Int) {
        setDescription()

        val spannableText = SpannableString(binding.tvJoinGroupSuccessDescriptionGroupName.text)

        spannableText.setSpan(
            TextAppearanceSpan(
                this@JoinGroupSuccessActivity,
                R.style.TextAppearance_Pingle_Sub_Semi_16
            ),
            GROUP_NAME_START,
            groupNameCount,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableText.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this@JoinGroupSuccessActivity,
                    R.color.g_01
                )
            ),
            GROUP_NAME_START,
            groupNameCount,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvJoinGroupSuccessDescriptionGroupName.text = spannableText
    }

    companion object {
        const val GROUP_NAME_START = 0
    }
}
