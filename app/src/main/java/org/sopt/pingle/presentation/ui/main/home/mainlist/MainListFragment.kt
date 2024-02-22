package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMainListBinding
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.stringOf

@AndroidEntryPoint
class MainListFragment : BindingFragment<FragmentMainListBinding>(R.layout.fragment_main_list) {
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectData()
    }

    private fun collectData() {
        homeViewModel.mainListOrderType.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { mainListOrderType ->
                binding.tvMainListOrderType.text =
                    stringOf(mainListOrderType.mainListOrderStringRes)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
