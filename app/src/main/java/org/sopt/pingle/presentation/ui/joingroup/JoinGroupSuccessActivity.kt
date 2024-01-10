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
import org.sopt.pingle.util.base.BindingActivity

class JoinGroupSuccessActivity :
    BindingActivity<ActivityJoinGroupSuccessBinding>(R.layout.activity_join_group_success) {
    private lateinit var groupName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        // TODO 이전 화면에서 Intent를 통해서 groupName을 가져옴
        // groupName = intent.getStringExtra(JoinGroupCodeActivity.GROUP_NAME).toString()
        groupName = "SOPT"

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
    }
}
