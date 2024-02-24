package org.sopt.pingle.presentation.ui.mygroup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupBinding
import org.sopt.pingle.databinding.ItemMyGroupOwnerBinding
import org.sopt.pingle.domain.model.GroupListEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MyGroupAdapter(
    private val groupOnClick: () -> Unit,
) : ListAdapter<GroupListEntity, RecyclerView.ViewHolder>(
    ItemDiffCallback<GroupListEntity>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id },
    ),
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> MyGroupViewHolder(
                ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                groupOnClick,
            )
            2 -> MyGroupOwnerViewHolder(
                ItemMyGroupOwnerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                groupOnClick,
            )
            else -> throw RuntimeException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyGroupViewHolder -> holder.onBind(currentList[position])
            is MyGroupOwnerViewHolder -> holder.onBind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isOwner) {
            2
        } else {
            1
        }
    }
}
