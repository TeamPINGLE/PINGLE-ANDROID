package org.sopt.pingle.presentation.ui.newgroup

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupKeywordBinding
import org.sopt.pingle.util.base.BindingFragment

@AndroidEntryPoint
class NewGroupKeywordFragment :
    BindingFragment<FragmentNewGroupKeywordBinding>(R.layout.fragment_new_group_keyword) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
