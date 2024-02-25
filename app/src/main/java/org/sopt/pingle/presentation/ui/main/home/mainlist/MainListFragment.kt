package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMainListBinding
import org.sopt.pingle.presentation.type.MainListOrderType
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.toPx
import org.sopt.pingle.util.view.PingleCardUtils

@AndroidEntryPoint
class MainListFragment : BindingFragment<FragmentMainListBinding>(R.layout.fragment_main_list) {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var mainListAdapter: MainListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroyView() {
        binding.rvMainList.adapter = null
        super.onDestroyView()
    }

    private fun initLayout() {
        mainListAdapter = MainListAdapter(
            navigateToParticipant = { id ->
                context?.let {
                    PingleCardUtils.navigateToParticipant(
                        it,
                        id
                    )
                }
            },
            navigateToWebViewWithChatLink = { chatLink -> startActivity(navigateToWebView(chatLink)) },
            showPingleJoinModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleJoinModalDialogFragment(
                    fragment = this,
                    postPingleJoin = { homeViewModel.postPingleJoin(pingleEntity.id) },
                    pingleEntity = pingleEntity
                )
            },
            showPingleCancelModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleCancelModalDialogFragment(
                    fragment = this,
                    deletePingleCancel = { homeViewModel.deletePingleCancel(pingleEntity.id) }
                )
            },
            showPingleDeleteModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showMapDeleteModalDialogFragment(
                    fragment = this,
                    deletePingleDelete = { homeViewModel.deletePingleDelete(pingleEntity.id) }
                )
            }
        )
        binding.rvMainList.adapter = mainListAdapter
        mainListAdapter.submitList(homeViewModel.dummyPingleList)

        (homeViewModel.searchWord.isBlank()).let { isNotSearching ->
            with(binding.tvMainListSearchCount) {
                visibility = if (isNotSearching) View.INVISIBLE else View.VISIBLE
                layoutParams = (this.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    topMargin =
                        (if (isNotSearching) SEARCH_COUNT_TOP_MARGIN_WHEN_NOT_SEARCHING else SEARCH_COUNT_TOP_MARGIN_WHEN_SEARCHING).toPx()
                }
                text = getString(
                    R.string.main_list_search_count,
                    homeViewModel.dummyPingleList.size
                )
            }

            binding.tvMainListEmpty.text =
                if (isNotSearching) stringOf(R.string.main_list_empty_pingle) else stringOf(
                    R.string.main_list_empty_search
                )
        }

        // TODO 서버통신 구현 후 collectData 함수로 해당 로직 이동
        with(homeViewModel.dummyPingleList) {
            binding.tvMainListEmpty.visibility = if (isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun addListeners() {
        with(binding) {
            layoutMainListOrder.setOnClickListener {
                (layoutMainListOrderMenu.visibility == View.VISIBLE).let { isVisible ->
                    layoutMainListOrderMenu.visibility =
                        if (isVisible) View.INVISIBLE else View.VISIBLE
                }
            }

            tvMainListOrderMenuNew.setOnClickListener {
                homeViewModel.setMainListOrderType(
                    MainListOrderType.NEW
                )
                layoutMainListOrderMenu.visibility = View.INVISIBLE
            }

            tvMainListOrderMenuUpcoming.setOnClickListener {
                homeViewModel.setMainListOrderType(
                    MainListOrderType.UPCOMING
                )
                layoutMainListOrderMenu.visibility = View.INVISIBLE
            }


        }
    }

    private fun collectData() {
        homeViewModel.mainListOrderType.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { mainListOrderType ->
                binding.tvMainListOrderType.text =
                    stringOf(mainListOrderType.mainListOrderStringRes)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val SEARCH_COUNT_TOP_MARGIN_WHEN_NOT_SEARCHING = 127
        private const val SEARCH_COUNT_TOP_MARGIN_WHEN_SEARCHING = 147
    }
}
