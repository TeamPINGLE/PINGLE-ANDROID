package org.sopt.pingle.presentation.ui.newgroup

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupAnnouncementBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupAnnouncementActivity :
    BindingActivity<ActivityNewGroupAnnouncementBinding>(R.layout.activity_new_group_announcement) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        onBackPressedCallBack()
        binding.btnNewGroupAnnouncementInvitation.setOnClickListener {
            navigateToNewGroupShare()
        }
        binding.tvNewGroupAnnouncementHome.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToNewGroupShare() {
        Intent(this, NewGroupShareActivity::class.java).apply {
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
                    finish()
                }
            }
        )
    }
}
