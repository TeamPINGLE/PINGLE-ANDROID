package org.sopt.pingle.presentation.ui.newgroup.newgroupkeyword

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupKeywordBinding
import org.sopt.pingle.domain.model.NewGroupKeywordEntity
import org.sopt.pingle.presentation.ui.newgroup.NewGroupViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class NewGroupKeywordFragment :
    BindingFragment<FragmentNewGroupKeywordBinding>(R.layout.fragment_new_group_keyword) {
    private val newGroupViewModel: NewGroupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        collectData()
    }

    override fun onResume() {
        super.onResume()

        with(newGroupViewModel.newGroupKeywordValue.value) {
            if (isNotEmpty()) {
                binding.cgNewGroupKeyword.forEach { childChip ->
                    (childChip as Chip).let { chip ->
                        if (chip.text == this) chip.isChecked = true
                    }
                }
            }
        }
    }

    private fun initLayout() {
        newGroupViewModel.getNewGroupKeywords()
    }

    private fun collectData() {
        newGroupViewModel.newGroupKeywordsState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> setChipKeyword(uiState.data)

                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun setChipKeyword(keywords: List<NewGroupKeywordEntity>) {
        binding.cgNewGroupKeyword.removeAllViews()

        for (item in keywords) {
            val chip =
                layoutInflater.inflate(R.layout.view_new_group_chip_keyword, null, false) as Chip
            chip.text = item.value
            chip.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        chip.setTextAppearance(R.style.TextAppearance_Pingle_Sub_Semi_16)
                        newGroupViewModel.setNewGroupKeyword(
                            keywordName = item.name,
                            keywordValue = item.value
                        )
                    }

                    false -> {
                        chip.setTextAppearance(R.style.TextAppearance_Pingle_Body_Med_16)
                        newGroupViewModel.setNewGroupKeyword(
                            keywordName = "",
                            keywordValue = ""
                        )
                    }
                }
            }
            binding.cgNewGroupKeyword.addView(chip)
        }
    }
}
