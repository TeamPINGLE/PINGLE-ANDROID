package org.sopt.pingle.presentation.ui.newgroup.newgroupinput

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupInputBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.newgroup.NewGroupViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class NewGroupInputFragment :
    BindingFragment<FragmentNewGroupInputBinding>(R.layout.fragment_new_group_input) {
    private val newGroupViewModel: NewGroupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newGroupViewModel = newGroupViewModel

        addListeners()
        collectData()
    }

    private fun addListeners() {
        binding.etNewGroupInputGroupName.btnEditTextCheck.setOnClickListener { newGroupViewModel.getNewGroupCheckName() }
    }

    private fun collectData() {
        collectNewGroupTeamNameIsEnabled()
        collectNewGroupCheckNameState()
    }

    private fun collectNewGroupTeamNameIsEnabled() {
        newGroupViewModel.newGroupName.flowWithLifecycle(lifecycle).onEach { newGroupName ->
            binding.etNewGroupInputGroupName.btnEditTextCheck.isEnabled = newGroupName.isNotBlank()
            newGroupViewModel.setIsNewGroupBtnCheckName(false)
        }.launchIn(lifecycleScope)
    }

    private fun collectNewGroupCheckNameState() {
        newGroupViewModel.newGroupCheckNameState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    if (uiState.data.result) {
                        PingleSnackbar.makeSnackbar(
                            binding.root,
                            stringOf(R.string.new_group_input_snackbar_guide),
                            SNACKBAR_BOTTOM_MARGIN,
                            SnackbarType.GUIDE
                        )
                        binding.etNewGroupInputGroupName.btnEditTextCheck.isEnabled = false
                        newGroupViewModel.setIsNewGroupBtnCheckName(true)
                    } else {
                        PingleSnackbar.makeSnackbar(
                            binding.root,
                            stringOf(R.string.new_group_input_snackbar_warning),
                            SNACKBAR_BOTTOM_MARGIN,
                            SnackbarType.WARNING
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        const val SNACKBAR_BOTTOM_MARGIN = 97
    }
}
