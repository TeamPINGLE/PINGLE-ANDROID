package org.sopt.pingle.presentation.ui.main.mypingle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MyPingleAdatper() : ListAdapter<MyPingleEntity, MyPingleViewHolder>(
    ItemDiffCallback<MyPingleEntity>(
        onItemsTheSame = { old, new -> old.id == new.id },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPingleViewHolder =
        MyPingleViewHolder(
            ItemMyPingleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyPingleViewHolder, position: Int) {
        holder.onBinding(getItem(position))
    }

}