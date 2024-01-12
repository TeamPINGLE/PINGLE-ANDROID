package org.sopt.pingle.presentation.ui.joingroup

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemJoinGroupSearchBinding
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

class JoinGroupSearchViewHolder(
    private val binding: ItemJoinGroupSearchBinding,
    private val setOldItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            setOldItem(bindingAdapterPosition)
        }
    }

    fun onBind(item: JoinGroupSearchEntity) {
        binding.joinGroupSearch = item
    }
}