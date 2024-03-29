package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMainListBinding
import org.sopt.pingle.presentation.type.MainListOrderType
import org.sopt.pingle.presentation.type.PingleCardErrorType
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.main.home.HomeFragment.Companion.DELETED_PINGLE_MESSAGE
import org.sopt.pingle.presentation.ui.main.home.HomeFragment.Companion.SNACKBAR_BOTTOM_MARGIN
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.PingleCardUtils
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

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
                AmplitudeUtils.trackEvent(CLICK_CARD_PARTICIPANTS)
            },
            navigateToWebViewWithChatLink = { chatLink ->
                startActivity(navigateToWebView(chatLink))
                AmplitudeUtils.trackEvent(CLICK_CARD_CHAT)
            },
            showPingleJoinModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleJoinModalDialogFragment(
                    fragment = this,
                    postPingleJoin = {
                        homeViewModel.postPingleJoin(pingleEntity.id)
                        AmplitudeUtils.trackEvent(CLICK_CARD_PARTICIPATE)
                    },
                    pingleEntity = pingleEntity
                )
            },
            showPingleCancelModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showPingleCancelModalDialogFragment(
                    fragment = this,
                    deletePingleCancel = {
                        homeViewModel.deletePingleCancel(pingleEntity.id)
                        AmplitudeUtils.trackEvent(CLICK_CARD_CANCEL)
                    }
                )
            },
            showPingleDeleteModalDialogFragment = { pingleEntity ->
                PingleCardUtils.showMapDeleteModalDialogFragment(
                    fragment = this,
                    deletePingleDelete = {
                        homeViewModel.deletePingleDelete(pingleEntity.id)
                        AmplitudeUtils.trackEvent(CLICK_CARD_DELETE)
                    }
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
                AmplitudeUtils.trackEvent(CLICK_DATESOONARRAY)
                homeViewModel.setMainListOrderType(
                    MainListOrderType.NEW
                )
                layoutMainListOrderMenu.visibility = View.INVISIBLE
            }

            tvMainListOrderMenuUpcoming.setOnClickListener {
                AmplitudeUtils.trackEvent(CLICK_RECENTCREATIONARRAY)
                homeViewModel.setMainListOrderType(
                    MainListOrderType.UPCOMING
                )
                layoutMainListOrderMenu.visibility = View.INVISIBLE
            }
        }
    }

    private fun collectData() {
        homeViewModel.mainListPingleListState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.CREATED
        )
            .onEach { mainListPingleListUiState ->
                when (mainListPingleListUiState) {
                    is UiState.Success -> {
                        mainListPingleListUiState.data.let { mainListPingleList ->
                            mainListAdapter.submitList(mainListPingleList)
                            with(binding) {
                                rvMainList.smoothScrollToPosition(TOP)
                                tvMainListEmpty.visibility =
                                    if (mainListPingleList.isEmpty()) View.VISIBLE else View.INVISIBLE
                            }
                        }

                        binding.tvMainListOrderType.text =
                            stringOf(homeViewModel.pingleFilter.value.mainListOrderType.mainListOrderStringRes)

                        (!homeViewModel.pingleFilter.value.searchWord.isNullOrEmpty()).let { isSearching ->
                            AmplitudeUtils.trackEventWithProperty(
                                eventName = COMPLETE_SEARCH_LIST,
                                propertyName = KEYWORD,
                                propertyValue = homeViewModel.pingleFilter.value.searchWord
                            )
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
                        with(mainListAdapter) {
                            submitList(currentList.map { mainListPingleModel -> if (mainListPingleModel.pingleEntity.id == pingleParticipationUiState.data) mainListPingleModel.updateMainListPingleModelJoin() else mainListPingleModel })
                        }
                    }

                    is UiState.Error -> {
                        when (pingleParticipationUiState.code) {
                            PingleCardErrorType.DELETED.code -> {
                                if (DELETED_PINGLE_MESSAGE.contains(pingleParticipationUiState.message)) {
                                    homeViewModel.getMainListPingleList()
                                    showErrorSnackbar(errorType = PingleCardErrorType.DELETED)
                                }
                            }

                            PingleCardErrorType.COMPLETED.code -> {
                                with(mainListAdapter) {
                                    submitList(currentList.map { mainListPingleModel -> if (mainListPingleModel.pingleEntity.id == pingleParticipationUiState.data) mainListPingleModel.updateMainListPingleModelCompleted() else mainListPingleModel })
                                }
                                showErrorSnackbar(errorType = PingleCardErrorType.COMPLETED)
                            }

                            else -> Timber.d(pingleParticipationUiState.message)
                        }
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

    private fun showErrorSnackbar(errorType: PingleCardErrorType) {
        PingleSnackbar.makeSnackbar(
            view = requireView(),
            message = stringOf(errorType.snackbarStringRes),
            bottomMarin = SNACKBAR_BOTTOM_MARGIN,
            snackbarType = SnackbarType.GUIDE
        )
    }

    companion object {
        private const val TOP = 0

        private const val COMPLETE_SEARCH_LIST = "complete_search_list"
        private const val KEYWORD = "keyword"
        private const val CLICK_DATESOONARRAY = "click_datesoonarray"
        private const val CLICK_RECENTCREATIONARRAY = "click_recentcreationarray"
        private const val CLICK_CARD_PARTICIPANTS = "click_card_participants"
        private const val CLICK_CARD_CHAT = "click_card_chat"
        private const val CLICK_CARD_PARTICIPATE = "click_card_participate"
        private const val CLICK_CARD_CANCEL = "click_card_cancel"
        private const val CLICK_CARD_DELETE = "click_card_delete"
    }
}
