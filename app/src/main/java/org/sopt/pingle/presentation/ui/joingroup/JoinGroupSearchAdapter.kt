package org.sopt.pingle.presentation.ui.joingroup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemJoinGroupSearchBinding
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class JoinGroupSearchAdapter(
    private val setOldItem: (Int) -> Unit
) :
    ListAdapter<JoinGroupSearchEntity, JoinGroupSearchViewHolder>(
        ItemDiffCallback<JoinGroupSearchEntity>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JoinGroupSearchViewHolder(
        ItemJoinGroupSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        setOldItem
    )

    override fun onBindViewHolder(holder: JoinGroupSearchViewHolder, position: Int) =
        holder.onBind(getItem(position))
}

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
