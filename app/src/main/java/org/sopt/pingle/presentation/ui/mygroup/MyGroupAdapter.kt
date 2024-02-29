package org.sopt.pingle.presentation.ui.mygroup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyGroupDefaultBinding
import org.sopt.pingle.databinding.ItemMyGroupOwnerBinding
import org.sopt.pingle.domain.model.MyGroupEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MyGroupAdapter(
    private val groupOnClick: (Int) -> Unit,
) : ListAdapter<MyGroupEntity, RecyclerView.ViewHolder>(
    ItemDiffCallback<MyGroupEntity>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id },
    ),
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DEFAULT_GROUP -> MyGroupViewHolder(
                ItemMyGroupDefaultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                groupOnClick,
            )

            OWNER_GROUP -> MyGroupOwnerViewHolder(
                ItemMyGroupOwnerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                groupOnClick,
            )

            else -> throw IllegalArgumentException("$VIEWTYPE_EXCEPTION_MESSAGE $viewType")
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
            OWNER_GROUP
        } else {
            DEFAULT_GROUP
        }
    }

    companion object {
        private const val OWNER_GROUP = 2
        private const val DEFAULT_GROUP = 1
        private const val VIEWTYPE_EXCEPTION_MESSAGE = "Invalid viewType: "
    }
}
