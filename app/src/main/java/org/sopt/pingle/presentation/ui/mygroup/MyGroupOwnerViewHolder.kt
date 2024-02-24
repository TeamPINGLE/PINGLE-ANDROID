package org.sopt.pingle.presentation.ui.mygroup

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupOwnerBinding
import org.sopt.pingle.domain.model.GroupListEntity

class MyGroupOwnerViewHolder(
    private val binding: ItemMyGroupOwnerBinding,
    private val groupOnClick: () -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(groupListEntity: GroupListEntity) {
        with(binding){
            this.groupListEntity = groupListEntity
            layoutMyGroupListOwner.setOnClickListener { groupOnClick() }
        }
    }
}
