package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.presentation.ui.main.home.map.MapFragment.Companion.MEETING_ID
import org.sopt.pingle.presentation.ui.participant.ParticipantActivity
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class MyPingleFragment : BindingFragment<FragmentMyPingleBinding>(R.layout.fragment_my_pingle) {
    private val viewModel by viewModels<MyPingleViewModel>()
    private lateinit var myPingleAdapter: MyPingleAdatper

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
        myPingleAdapter = MyPingleAdatper(
            requireContext(),
            showCancelModalDialogFragment = ::showCancelModalDialogFragment,
            showDeleteModalDialogFragment = ::showDeleteModalDialogFragment,
            updateMyPingleListSelectedPosition = ::updateMyPingleListSelectedPosition,
            clearMyPingleListSelection = ::clearMyPingleListSelection,
            navigateToParticipation = ::navigateToParticipation
        )
        binding.rvMyPingle.adapter = myPingleAdapter
    }

    private fun addListeners() {
        binding.tlMyPingle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    MyPingleType.SOON.tabPosition -> viewModel.setMyPingleType(MyPingleType.SOON)
                    MyPingleType.DONE.tabPosition -> viewModel.setMyPingleType(MyPingleType.DONE)
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
            clickBtn = { viewModel.deletePingleCancel(meetingId = myPingleEntity.id.toLong()) },
            clickTextBtn = { }
        ).show(childFragmentManager, MY_PINGLE_CANCEL_MODAL)
    }

    private fun showDeleteModalDialogFragment(myPingleEntity: MyPingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.delete_modal_title),
            detail = stringOf(R.string.delete_modal_detail),
            buttonText = stringOf(R.string.delete_modal_button_text),
            textButtonText = stringOf(R.string.delete_modal_text_button_text),
            clickBtn = { viewModel.deletePingleDelete(meetingId = myPingleEntity.id.toLong()) },
            clickTextBtn = {}
        ).show(childFragmentManager, MY_PINGEL_DELETE_MODAL)
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
        const val MY_PINGLE_CANCEL_MODAL = "MyPingleCancelModal"
        const val MY_PINGEL_DELETE_MODAL = "MyPingelDeleteModal"
    }
}
