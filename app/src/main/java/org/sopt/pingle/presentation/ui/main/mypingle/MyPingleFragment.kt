package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.presentation.type.PingleCardErrorType
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment.Companion.MEETING_ID
import org.sopt.pingle.presentation.ui.participant.ParticipantActivity
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class MyPingleFragment : BindingFragment<FragmentMyPingleBinding>(R.layout.fragment_my_pingle) {
    private val viewModel by viewModels<MyPingleViewModel>()
    private lateinit var myPingleAdapter: MyPingleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroyView() {
        binding.rvMyPingle.adapter = null
        super.onDestroyView()
    }

    private fun initLayout() {
        myPingleAdapter = MyPingleAdapter(
            showCancelModalDialogFragment = ::showCancelModalDialogFragment,
            showDeleteModalDialogFragment = ::showDeleteModalDialogFragment,
            updateMyPingleListSelectedPosition = ::updateMyPingleListSelectedPosition,
            clearMyPingleListSelection = ::clearMyPingleListSelection,
            navigateToParticipation = ::navigateToParticipation
        )
        binding.rvMyPingle.adapter = myPingleAdapter

        binding.rvMyPingle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    when (viewModel.myPingleType.value) {
                        MyPingleType.SOON -> AmplitudeUtils.trackEvent(SCROLL_SOONPINGLE)
                        MyPingleType.DONE -> AmplitudeUtils.trackEvent(SCROLL_DONEPINGLE)
                    }
                }
            }
        })
    }

    private fun addListeners() {
        binding.tlMyPingle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    MyPingleType.SOON.tabPosition -> {
                        viewModel.setMyPingleType(MyPingleType.SOON)
                        AmplitudeUtils.trackEvent(CLICK_SOONPINGLE)
                    }

                    MyPingleType.DONE.tabPosition -> {
                        viewModel.setMyPingleType(MyPingleType.DONE)
                        AmplitudeUtils.trackEvent(CLICK_DONEPINGLE)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun collectData() {
        viewModel.myPingleType.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .distinctUntilChanged()
            .onEach {
                viewModel.getPingleParticipationList()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.myPingleListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        myPingleAdapter.submitList(uiState.data)
                        binding.tvMyPingleEmpty.visibility = View.INVISIBLE
                    }

                    is UiState.Empty -> {
                        myPingleAdapter.submitList(null)
                        binding.tvMyPingleEmpty.visibility = View.VISIBLE
                        binding.tvMyPingleEmpty.text = when (viewModel.myPingleType.value) {
                            MyPingleType.SOON -> stringOf(R.string.my_pingle_soon)
                            MyPingleType.DONE -> stringOf(R.string.my_pingle_done)
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.myPingleState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> viewModel.getPingleParticipationList()
                    is UiState.Error -> {
                        when (uiState.code) {
                            PingleCardErrorType.DELETED.code -> if (uiState.message == DELETED_PINGLE_MESSAGE) {
                                viewModel.getPingleParticipationList()
                                PingleSnackbar.makeSnackbar(
                                    view = requireView(),
                                    message = stringOf(PingleCardErrorType.DELETED.snackbarStringRes),
                                    bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                                    snackbarType = SnackbarType.GUIDE
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showCancelModalDialogFragment(myPingleEntity: MyPingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.cancel_modal_title),
            detail = stringOf(R.string.cancel_modal_detail),
            buttonText = stringOf(R.string.cancel_modal_button_text),
            textButtonText = stringOf(R.string.cancel_modal_text_button_text),
            clickBtn = {
                viewModel.deletePingleCancel(meetingId = myPingleEntity.id.toLong())
                AmplitudeUtils.trackEvent(CLICK_SOONPINGLE_MORE_CANCEL)
            },
            clickTextBtn = { AmplitudeUtils.trackEvent(CLICK_SOONPINGLE_MORE_CANCEL_BACK) }
        ).show(childFragmentManager, MY_PINGLE_CANCEL_MODAL)
    }

    private fun showDeleteModalDialogFragment(myPingleEntity: MyPingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.delete_modal_title),
            detail = stringOf(R.string.delete_modal_detail),
            buttonText = stringOf(R.string.delete_modal_button_text),
            textButtonText = stringOf(R.string.delete_modal_text_button_text),
            clickBtn = {
                viewModel.deletePingleDelete(meetingId = myPingleEntity.id.toLong())
                AmplitudeUtils.trackEvent(CLICK_SOONPINGLE_MORE_DELETE)
            },
            clickTextBtn = {}
        ).show(childFragmentManager, MY_PINGLE_DELETE_MODAL)
    }

    private fun updateMyPingleListSelectedPosition(position: Int) {
        viewModel.updateMyPingleList(position)
    }

    private fun clearMyPingleListSelection() {
        viewModel.clearMyPingleListSelection()
    }

    private fun navigateToParticipation(meetingId: Long) {
        Intent(context, ParticipantActivity::class.java).apply {
            putExtra(MEETING_ID, meetingId)
            startActivity(this)
        }
    }

    companion object {
        const val SNACKBAR_BOTTOM_MARGIN = 76
        const val DELETED_PINGLE_MESSAGE = "존재하지 않는 유저미팅입니다."
        const val MY_PINGLE_CANCEL_MODAL = "MyPingleCancelModal"
        const val MY_PINGLE_DELETE_MODAL = "MyPingleDeleteModal"
        private const val CLICK_SOONPINGLE = "click_soonpingle"
        private const val SCROLL_SOONPINGLE = "scroll_soonpingle"
        private const val CLICK_SOONPINGLE_MORE_CANCEL = "click_soonpingle_more_cancel"
        private const val CLICK_SOONPINGLE_MORE_CANCEL_BACK = "click_soonpingle_more_cancel_back"
        private const val CLICK_SOONPINGLE_MORE_DELETE = "click_soonpingle_more_delete"
        private const val CLICK_DONEPINGLE = "click_donepingle"
        private const val SCROLL_DONEPINGLE = "scroll_donepingle"
    }
}
