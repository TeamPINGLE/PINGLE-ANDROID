package org.sopt.pingle.presentation.ui.newgroup

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupShareBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class NewGroupShareActivity :
    BindingActivity<ActivityNewGroupShareBinding>(R.layout.activity_new_group_share) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
    }

    private fun addListeners() {
        binding.includeNewGroupShareTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }
}
