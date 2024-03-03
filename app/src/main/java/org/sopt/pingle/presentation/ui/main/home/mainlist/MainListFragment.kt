package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMainListBinding
import org.sopt.pingle.presentation.type.MainListOrderType
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.PingleCardUtils
import org.sopt.pingle.util.view.UiState

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
        combine(
            homeViewModel.searchWord.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged(),
            homeViewModel.category.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged(),
            homeViewModel.mainListOrderType.flowWithLifecycle(viewLifecycleOwner.lifecycle).distinctUntilChanged()
        ) { _, _, _ ->
        }.onEach {
            homeViewModel.getMainListPingleList()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.mainListPingleListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { mainListPingleListUiState ->
                when (mainListPingleListUiState) {
                    is UiState.Success -> {
                        mainListPingleListUiState.data.let { mainListPingleList ->
                            mainListAdapter.submitList(mainListPingleList)
                            binding.rvMainList.smoothScrollToPosition(TOP)
                            binding.tvMainListEmpty.visibility = if (mainListPingleList.isEmpty()) View.VISIBLE else View.INVISIBLE
                        }

                        binding.tvMainListOrderType.text =
                            stringOf(homeViewModel.mainListOrderType.value.mainListOrderStringRes)

                        (!homeViewModel.searchWord.value.isNullOrBlank()).let { isSearching ->
                            with(binding.tvMainListSearchCount) {
                                visibility = if (isSearching) View.VISIBLE else View.INVISIBLE
                                text = getString(
                                    R.string.main_list_search_count,
                                    mainListPingleListUiState.data.size
                                )
                            }

                            binding.tvMainListEmpty.text =
                                if (isSearching) {
                                    stringOf(R.string.main_list_empty_search)
                                } else {
                                    stringOf(
                                        R.string.main_list_empty_pingle
                                    )
                                }
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.pingleParticipationState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { pingleParticipationUiState ->
                when (pingleParticipationUiState) {
                    is UiState.Success -> {
                        // TODO jihyun 아요와 논의 후 리스트뷰 초기화 구현
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        homeViewModel.pingleDeleteState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { pingleDeleteState ->
                when (pingleDeleteState) {
                    is UiState.Success -> {
                        homeViewModel.getMainListPingleList()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val TOP = 0
    }
}
