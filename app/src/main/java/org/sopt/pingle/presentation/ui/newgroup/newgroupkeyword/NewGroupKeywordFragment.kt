package org.sopt.pingle.presentation.ui.newgroup.newgroupkeyword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupKeywordBinding
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity
import org.sopt.pingle.presentation.ui.newgroup.NewGroupViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class NewGroupKeywordFragment :
    BindingFragment<FragmentNewGroupKeywordBinding>(R.layout.fragment_new_group_keyword) {
    private val newGroupViewModel by viewModels<NewGroupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        collectData()
    }

    private fun initLayout() {
        newGroupViewModel.getNewGroupKeywords()
    }

    private fun collectData() {
        newGroupViewModel.newGroupKeywords.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    setChipKeyword(uiState.data)
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun setChipKeyword(keywords: List<NewGroupKeywordsEntity>) {
        binding.cgNewGroupKeyword.removeAllViews()

        for (item in keywords) {
            val chip =
                layoutInflater.inflate(R.layout.view_new_group_chip_keyword, null, false) as Chip
            chip.text = item.value
            chip.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> chip.setTextAppearance(R.style.TextAppearance_Pingle_Sub_Semi_16)
                    false -> chip.setTextAppearance(R.style.TextAppearance_Pingle_Body_Med_16)
                }
            }
            binding.cgNewGroupKeyword.addView(chip)
        }
    }
}
