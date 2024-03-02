package org.sopt.pingle.presentation.ui.newgroup.newgroupinput

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupInputBinding
import org.sopt.pingle.util.base.BindingFragment

@AndroidEntryPoint
class NewGroupInputFragment :
    BindingFragment<FragmentNewGroupInputBinding>(R.layout.fragment_new_group_input) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
