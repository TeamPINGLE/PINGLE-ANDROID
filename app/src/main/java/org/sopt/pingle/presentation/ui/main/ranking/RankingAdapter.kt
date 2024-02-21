package org.sopt.pingle.presentation.ui.main.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemRankingBinding
import org.sopt.pingle.domain.model.RankingLocationEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class RankingAdapter :
    ListAdapter<RankingLocationEntity, RankingViewHolder>(
        ItemDiffCallback<RankingLocationEntity>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new }
        )
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder =
        RankingViewHolder(
            binding = ItemRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = parent.context
        )

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.onBind(rankingLocationEntity = getItem(position), ranking = position + 1)
    }
}
