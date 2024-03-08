package org.sopt.pingle.presentation.ui.main.ranking

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentRankingBinding
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class RankingFragment : BindingFragment<FragmentRankingBinding>(R.layout.fragment_ranking) {
    private val rankingViewModel by viewModels<RankingViewModel>()
    private lateinit var rankingAdapter: RankingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        collectData()
    }

    override fun onDestroyView() {
        binding.rvRanking.adapter = null
        super.onDestroyView()
    }

    private fun initLayout() {
        rankingViewModel.getRanking()

        rankingAdapter = RankingAdapter()

        with(binding.rvRanking) {
            adapter = rankingAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        AmplitudeUtils.trackEvent(SCROLL_RANKING)
                    }
                }
            })
        }
    }

    private fun collectData() {
        rankingViewModel.rankingState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { rankingState ->
                when (rankingState) {
                    is UiState.Success -> {
                        with(rankingState.data) {
                            (meetingCount >= RANKING_VISIBLE_THRESHOLD).let { isRankingVisible ->
                                if (isRankingVisible) rankingAdapter.submitList(locations)
                                binding.tvRankingEmpty.visibility =
                                    if (isRankingVisible) View.INVISIBLE else View.VISIBLE
                            }
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        const val RANKING_VISIBLE_THRESHOLD = 30
        private const val SCROLL_RANKING = "scroll_ranking"
    }
}
