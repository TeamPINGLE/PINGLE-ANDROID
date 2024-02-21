package org.sopt.pingle.presentation.ui.main.ranking

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentRankingBinding
import org.sopt.pingle.util.base.BindingFragment

class RankingFragment : BindingFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val viewModel by viewModels<RankingViewModel>()
    private lateinit var rankingAdapter: RankingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
    }

    override fun onDestroyView() {
        binding.rvRanking.adapter = null
        super.onDestroyView()
    }

    private fun initLayout() {
        rankingAdapter = RankingAdapter()
        binding.rvRanking.adapter = rankingAdapter

        // TODO 서버통신 구현 후 collectData 함수로 해당 로직 이동
        with(viewModel.dummyRanking) {
            (meetingCount >= RANKING_VISIBLE_THRESHOLD).let { isRankingVisible ->
                if (isRankingVisible) rankingAdapter.submitList(locations)
                binding.tvRankingEmpty.visibility =
                    if (isRankingVisible) View.INVISIBLE else View.VISIBLE
            }
        }
    }

    companion object {
        const val RANKING_VISIBLE_THRESHOLD = 30
    }
}
