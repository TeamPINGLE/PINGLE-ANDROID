package org.sopt.pingle.presentation.ui.newgroup.newgroupcreate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupCreateBinding
import org.sopt.pingle.presentation.ui.newgroup.NewGroupViewModel
import org.sopt.pingle.util.base.BindingFragment

@AndroidEntryPoint
class NewGroupCreateFragment :
    BindingFragment<FragmentNewGroupCreateBinding>(R.layout.fragment_new_group_create) {
    private val newGroupViewModel: NewGroupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newGroupViewModel = newGroupViewModel
    }
}
