package org.sopt.pingle.presentation.ui.newgroup.newgroupannouncement

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupAnnouncementBinding
import org.sopt.pingle.presentation.model.NewGroupModel
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupcodeshare.NewGroupCodeShareActivity
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.Intent.getCompatibleParcelableExtra
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.makeEllipsisGroupName

@AndroidEntryPoint
class NewGroupAnnouncementActivity :
    BindingActivity<ActivityNewGroupAnnouncementBinding>(R.layout.activity_new_group_announcement) {
    private lateinit var newGroupModel: NewGroupModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        newGroupModel = intent.getCompatibleParcelableExtra(NEW_GROUP_CODE) ?: NewGroupModel("", "")
        spannableGroupName()
    }

    private fun addListeners() {
        with(binding) {
            btnNewGroupAnnouncementInvitation.setOnClickListener {
                navigateToNewGroupShare()
                AmplitudeUtils.trackEvent(CLICK_CREATEGROUP_INVITE)
            }
            tvNewGroupAnnouncementHome.setOnClickListener {
                navigateToHome()
                AmplitudeUtils.trackEvent(CLICK_CREATEGROUP_START)
            }
        }
        onBackPressedCallBack()
    }

    private fun navigateToNewGroupShare() {
        Intent(this, NewGroupCodeShareActivity::class.java).apply {
            putExtra(
                NEW_GROUP_CODE,
                newGroupModel
            )
            startActivity(this)
        }
    }

    private fun navigateToHome() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun onBackPressedCallBack() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToHome()
                }
            }
        )
    }

    private fun spannableGroupName() {
        val newGroupName = newGroupModel.name.makeEllipsisGroupName()

        binding.tvNewGroupAnnouncementGroupName.text = SpannableString(
            getString(R.string.new_group_announcement_group_name, newGroupName)
        ).apply {
            setSpan(
                TextAppearanceSpan(
                    this@NewGroupAnnouncementActivity,
                    R.style.TextAppearance_Pingle_Sub_Semi_16
                ),
                GROUP_NAME_START,
                newGroupName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@NewGroupAnnouncementActivity,
                        R.color.g_01
                    )
                ),
                GROUP_NAME_START,
                newGroupName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    companion object {
        const val NEW_GROUP_CODE = "NewGroupCode"
        const val GROUP_NAME_START = 0
        private const val CLICK_CREATEGROUP_INVITE = "click_creategroup_invite"
        private const val CLICK_CREATEGROUP_START = "click_creategroup_start"
    }
}
