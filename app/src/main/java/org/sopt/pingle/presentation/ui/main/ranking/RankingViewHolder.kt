package org.sopt.pingle.presentation.ui.main.ranking

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemRankingBinding
import org.sopt.pingle.domain.model.RankingLocationEntity

class RankingViewHolder(private val binding: ItemRankingBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(rankingLocationEntity: RankingLocationEntity, ranking: Int) {
        with(binding) {
            tvRankingRank.text = (ranking).toString()
            tvRankingLocationCount.text = rankingLocationEntity.locationCount.toString()
            tvRankingLocationName.text = rankingLocationEntity.name
            (rankingLocationEntity.latestVisitedDate).let { latestVisitedDate ->
                tvRankingLocationDate.text = context.getString(
                    R.string.ranking_date,
                    latestVisitedDate[YEAR_INDEX],
                    latestVisitedDate[MONTH_INDEX],
                    latestVisitedDate[DAY_INDEX]
                )
            }
        }
    }

    companion object {
        const val YEAR_INDEX = 0
        const val MONTH_INDEX = 1
        const val DAY_INDEX = 2
    }
}
