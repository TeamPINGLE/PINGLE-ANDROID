package org.sopt.pingle.presentation.ui.joingroup

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupSuccessBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.activity.setDoubleBackPressToExit
import org.sopt.pingle.util.base.BindingActivity

class JoinGroupSuccessActivity :
    BindingActivity<ActivityJoinGroupSuccessBinding>(R.layout.activity_join_group_success) {
    private lateinit var groupName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        setDoubleBackPressToExit(binding.root)
    }

    private fun initLayout() {
        groupName = intent.getStringExtra(JoinGroupCodeActivity.GROUP_NAME).toString()

        binding.tvJoinGroupSuccessDescriptionGroupName.text = SpannableString(
            getString(
                R.string.join_group_success_description_group_name,
                groupName
            )
        ).apply {
            setSpan(
                TextAppearanceSpan(
                    this@JoinGroupSuccessActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                GROUP_NAME_START,
                groupName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@JoinGroupSuccessActivity,
                        R.color.g_01
                    )
                ),
                GROUP_NAME_START,
                groupName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun addListeners() {
        binding.btnJoinGroupSuccess.setOnClickListener {
            navigateToMain()
            AmplitudeUtils.trackEvent(CLICK_EXISTINGGROUP_START)
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            finish()
        }
    }

    companion object {
        const val GROUP_NAME_START = 0
        const val CLICK_EXISTINGGROUP_START = "click_existinggroup_start"
    }
}
