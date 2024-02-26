package org.sopt.pingle.presentation.ui.newgroup

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupCodeShareBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupCodeShareActivity :
    BindingActivity<ActivityNewGroupCodeShareBinding>(R.layout.activity_new_group_code_share) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.etNewGroupCodeShareCode.editText.isEnabled = false
    }

    private fun addListeners() {
        binding.includeNewGroupCodeShareTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }
}
