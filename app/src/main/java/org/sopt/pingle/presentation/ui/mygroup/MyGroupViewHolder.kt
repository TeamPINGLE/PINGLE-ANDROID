package org.sopt.pingle.presentation.ui.mygroup

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupBinding
import org.sopt.pingle.domain.model.GroupListEntity

class MyGroupViewHolder(
    private val binding: ItemMyGroupBinding,
    private val groupOnClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(groupListEntity: GroupListEntity) {
        with(binding) {
            this.groupListEntity = groupListEntity
            tvMyGroupListDefaultName.text = groupListEntity.name
            layoutMyGroupListDefault.setOnClickListener { groupOnClick(absoluteAdapterPosition) }
        }
    }
}
