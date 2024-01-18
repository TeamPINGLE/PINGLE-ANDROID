package org.sopt.pingle.presentation.ui.main.mypingle

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

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

    private fun initLayout() {
        myPingleAdapter = MyPingleAdatper(
            requireContext(),
            showDeleteModalDialogFragment = ::showDeleteModalDialogFragment,
            setOldItem = ::deleteOldPosition
        )
        binding.rvMyPingle.adapter = myPingleAdapter
        viewModel.getPingleParticipationList()
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
        viewModel.myPingleType.flowWithLifecycle(lifecycle).onEach {
            viewModel.getPingleParticipationList()
        }.launchIn(lifecycleScope)

        viewModel.myPingleState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    myPingleAdapter.submitList(uiState.data)
                    binding.tvMyPingleEmpty.visibility = View.INVISIBLE
                }

                is UiState.Error -> Timber.tag(MY_PINGLE_FRAGMENT).d(ERROR + uiState.message)
                is UiState.Loading -> Timber.tag(MY_PINGLE_FRAGMENT).d(LOADING)
                is UiState.Empty -> {
                    myPingleAdapter.submitList(null)
                    binding.tvMyPingleEmpty.visibility = View.VISIBLE
                    binding.tvMyPingleEmpty.text = when (viewModel.myPingleType.value) {
                        MyPingleType.SOON -> stringOf(R.string.my_pingle_soon)
                        MyPingleType.DONE -> stringOf(R.string.my_pingle_done)
                    }
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.myPingleCancelState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> viewModel.getPingleParticipationList()
                is UiState.Error -> Log.d("http error", uiState.message.toString())
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun showDeleteModalDialogFragment(myPingleEntity: MyPingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { viewModel.deletePingleCancel(meetingId = myPingleEntity.id.toLong()) },
            clickTextBtn = { }
        ).show(childFragmentManager, MY_PINGLE_MODAL)
    }

    private fun deleteOldPosition(position: Int) {
        viewModel.updateMyPingleList(position)
    }

    companion object {
        const val MY_PINGLE_MODAL = "MyPingleModal"
        const val MY_PINGLE_FRAGMENT = "MyPingleFragment"
        const val ERROR = "Error : "
        const val LOADING = "Loading"
    }
}
