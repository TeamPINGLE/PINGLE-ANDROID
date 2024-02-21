package org.sopt.pingle.presentation.ui.newgroup

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupInfoBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupInfoActivity :
    BindingActivity<ActivityNewGroupInfoBinding>(R.layout.activity_new_group_info) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.ivNewGroupInfoX.setOnClickListener {
            when (intent.getStringExtra("ActivityName")) {
                "NewGroupActivity" -> navigateToNewGroup()

                // TODO 기존단체입장하기에서 넘어오는 경우에 대한 분기처리

                else -> finish()
            }
        }
    }

    private fun navigateToNewGroup() {
        Intent(this, NewGroupActivity::class.java).apply {
            startActivity(this)
        }
    }
}
