package org.sopt.pingle.presentation.ui.main.mypingle

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBinding(item: MyPingleEntity) {
        binding.myPingleItem = item
    }
}