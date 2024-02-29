package org.sopt.pingle.presentation.ui.mygroup

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupOwnerBinding
import org.sopt.pingle.domain.model.MyGroupEntity

class MyGroupOwnerViewHolder(
    private val binding: ItemMyGroupOwnerBinding,
    private val groupOnClick: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(myGroupEntity: MyGroupEntity) {
        with(binding) {
            this.groupListEntity = myGroupEntity
            tvMyGroupOwnerName.text = myGroupEntity.name
            layoutMyGroupOwner.setOnClickListener { groupOnClick(absoluteAdapterPosition) }
        }
    }
}
