package org.sopt.pingle.presentation.ui.joingroup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
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

