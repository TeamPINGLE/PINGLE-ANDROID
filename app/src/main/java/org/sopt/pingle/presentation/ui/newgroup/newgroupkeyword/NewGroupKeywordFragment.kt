package org.sopt.pingle.presentation.ui.newgroup.newgroupkeyword

import android.os.Bundle
import android.view.View
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentNewGroupKeywordBinding
import org.sopt.pingle.util.base.BindingFragment

@AndroidEntryPoint
class NewGroupKeywordFragment :
    BindingFragment<FragmentNewGroupKeywordBinding>(R.layout.fragment_new_group_keyword) {
    private val itemList =
        listOf(Item(1, "연합동아리"), Item(2, "교내동아리"), Item(3, "학생회"), Item(4, "대학교"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    private fun initLayout() {
        setChipKeyword()
    }

    private fun setChipKeyword() {
        binding.cgNewGroupKeyword.removeAllViews()

        for (item in itemList) {
            val chip =
                layoutInflater.inflate(R.layout.view_new_group_chip_keyword, null, false) as Chip
            chip.text = item.name
            chip.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> chip.setTextAppearance(R.style.TextAppearance_Pingle_Sub_Semi_16)
                    false -> chip.setTextAppearance(R.style.TextAppearance_Pingle_Body_Med_16)
                }
            }
            binding.cgNewGroupKeyword.addView(chip)
        }
    }

    // TODO Api연결시 response로 변경 예정
    data class Item(val id: Int, val name: String)
}
