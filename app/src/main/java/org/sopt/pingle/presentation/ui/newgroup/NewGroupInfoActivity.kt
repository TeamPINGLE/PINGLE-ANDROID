package org.sopt.pingle.presentation.ui.newgroup

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
            finish()
        }
    }
}
