package org.sopt.pingle.presentation.ui.mygroup

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupDefaultBinding
import org.sopt.pingle.domain.model.MyGroupEntity

class MyGroupViewHolder(
    private val binding: ItemMyGroupDefaultBinding,
    private val groupOnClick: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(myGroupEntity: MyGroupEntity) {
        with(binding) {
            this.groupListEntity = myGroupEntity
            tvMyGroupDefaultName.text = myGroupEntity.name
            layoutMyGroupDefault.setOnClickListener { groupOnClick(absoluteAdapterPosition) }
        }
    }
}
