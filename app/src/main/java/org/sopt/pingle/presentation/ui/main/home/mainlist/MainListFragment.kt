package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMainListBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.MainListOrderType
import org.sopt.pingle.presentation.ui.main.home.HomeViewModel
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment
import org.sopt.pingle.util.component.PingleModalDialogFragment
import org.sopt.pingle.presentation.ui.participant.ParticipantActivity
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf

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
            navigateToParticipant = ::navigateToParticipant,
            navigateToWebViewWithChatLink = ::navigateToWebViewWithChatLink,
            showMapJoinModalDialogFragment = ::showMapJoinModalDialogFragment,
            showMapCancelModalDialogFragment = ::showMapCancelModalDialogFragment,
            showMapDeleteModalDialogFragment = ::showMapDeleteModalDialogFragment
        )
        binding.rvMainList.adapter = mainListAdapter
        mainListAdapter.submitList(homeViewModel.dummyPingleList)

        // TODO 서버통신 구현 후 collectData 함수로 해당 로직 이동
        with(homeViewModel.dummyPingleList) {
            binding.tvMainListEmpty.visibility = if (isEmpty()) View.VISIBLE else View.INVISIBLE
            binding.tvMainListEmpty.text = stringOf(R.string.main_list_empty_pingle)
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

    private fun navigateToParticipant(pingleEntityId: Long) {
        Intent(context, ParticipantActivity::class.java).apply {
            putExtra(MapFragment.MEETING_ID, pingleEntityId)
            startActivity(this)
        }
    }

    private fun navigateToWebViewWithChatLink(chatLink: String) {
        startActivity(navigateToWebView(chatLink))
    }

    private fun showMapCancelModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.cancel_modal_title),
            detail = stringOf(R.string.cancel_modal_detail),
            buttonText = stringOf(R.string.cancel_modal_button_text),
            textButtonText = stringOf(R.string.cancel_modal_text_button_text),
            clickBtn = { homeViewModel.deletePingleCancel(meetingId = pingleEntity.id) },
            clickTextBtn = { }
        ).show(childFragmentManager, MAP_CANCEL_MODAL)
    }

    private fun showMapJoinModalDialogFragment(pingleEntity: PingleEntity) {
        with(pingleEntity) {
            PingleModalDialogFragment(
                category = CategoryType.fromString(categoryName = category),
                name = name,
                ownerName = ownerName,
                clickBtn = { homeViewModel.postPingleJoin(meetingId = pingleEntity.id) }
            ).show(childFragmentManager, MAP_JOIN_MODAL)
        }
    }

    private fun showMapDeleteModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.delete_modal_title),
            detail = stringOf(R.string.delete_modal_detail),
            buttonText = stringOf(R.string.delete_modal_button_text),
            textButtonText = stringOf(R.string.delete_modal_text_button_text),
            clickBtn = { homeViewModel.deletePingleDelete(meetingId = pingleEntity.id) },
            clickTextBtn = {}
        ).show(childFragmentManager, MAP_DELETE_MODAL)
    }

    companion object {
        private const val MAP_CANCEL_MODAL = "mapCancelModal"
        private const val MAP_JOIN_MODAL = "mapJoinModal"
        private const val MAP_DELETE_MODAL = "mapDeleteModal"
    }
}
