package org.sopt.pingle.presentation.ui.main.mypingle

import android.os.Bundle
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
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.MyPingleType
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.navigateToFragment
import org.sopt.pingle.util.fragment.navigateToWebView
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
            navigateToMapList = ::navigateToMapList,
            navigateToWebViewWithChatLink = ::navigateToWebViewWithChatLink,
            showDeleteModalDialogFragment = ::showDeleteModalDialogFragment
        )
        binding.rvMyPingle.adapter = myPingleAdapter


        viewModel.getPingleParticipationList(MyPingleType.SOON.boolean)
    }

    private fun addListeners() {
        binding.tlMyPingle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.getPingleParticipationList(MyPingleType.SOON.boolean)

                    1 -> viewModel.getPingleParticipationList(MyPingleType.DONE.boolean)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 이전에 선택된 탭에 대한 처리
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 다시 선택된 탭에 대한 처리
            }
        })
    }

    private fun collectData() {
        viewModel.myPingleState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    myPingleAdapter.submitList(uiState.data)
                    binding.tvMyPingleEmpty.visibility = View.INVISIBLE
                }

                is UiState.Error -> Timber.tag(MY_PINGLE_FRAGMENT).d(ERROR + uiState.message)
                is UiState.Loading -> Timber.tag(MY_PINGLE_FRAGMENT).d(LODING)
                is UiState.Empty -> {
                    myPingleAdapter.submitList(null)
                    if (MyPingleType.SOON.boolean) binding.tvMyPingleEmpty.text =
                        getString(R.string.my_pingle_soon)
                    else binding.tvMyPingleEmpty.text =
                        getString(R.string.my_pingle_done)
                    binding.tvMyPingleEmpty.visibility = View.VISIBLE
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.myPingleCancelState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> viewModel.myPingleState

                else -> Unit
            }
        }
    }

    private fun navigateToMapList() {
        navigateToFragment<MainListFragment>()
    }

    private fun navigateToWebViewWithChatLink(chatLink: String) {
        startActivity(navigateToWebView(chatLink))
    }

    private fun showDeleteModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { viewModel.deletePingleCancel(meetingId = pingleEntity.id) },
            clickTextBtn = { }
        ).show(childFragmentManager, "")
    }

    companion object {
        const val MY_PINGLE_FRAGMENT = "MyPingleFragment"
        const val ERROR = "Error : "
        const val LODING = "Loging"
    }
}
