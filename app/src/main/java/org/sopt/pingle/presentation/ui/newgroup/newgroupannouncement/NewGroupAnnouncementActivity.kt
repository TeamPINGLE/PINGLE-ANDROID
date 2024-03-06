package org.sopt.pingle.presentation.ui.newgroup.newgroupannouncement

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupAnnouncementBinding
import org.sopt.pingle.presentation.model.NewGroupModel
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.newgroup.newgroupcodeshare.NewGroupCodeShareActivity
import org.sopt.pingle.util.Intent.getCompatibleParcelableExtra
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupAnnouncementActivity :
    BindingActivity<ActivityNewGroupAnnouncementBinding>(R.layout.activity_new_group_announcement) {
    private val newGroupModel =
        intent.getCompatibleParcelableExtra<NewGroupModel>(NEW_GROUP_CODE) ?: NewGroupModel("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.tvNewGroupAnnouncementGroupName.text =
            getString(R.string.new_group_announcement_group_name, newGroupModel.name)
    }

    private fun addListeners() {
        with(binding) {
            btnNewGroupAnnouncementInvitation.setOnClickListener { navigateToNewGroupShare() }
            tvNewGroupAnnouncementHome.setOnClickListener { navigateToHome() }
        }
        onBackPressedCallBack()
    }

    private fun navigateToNewGroupShare() {
        Intent(this, NewGroupCodeShareActivity::class.java).apply {
            putExtra(
                NEW_GROUP_CODE,
                newGroupModel.code
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

    companion object {
        const val NEW_GROUP_CODE = "NewGroupCode"
    }
}
