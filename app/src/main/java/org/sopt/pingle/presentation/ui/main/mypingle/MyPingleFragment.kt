package org.sopt.pingle.presentation.ui.main.mypingle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentMyPingleBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.ui.main.home.mainlist.MainListFragment
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.navigateToFragment
import org.sopt.pingle.util.fragment.stringOf

class MyPingleFragment : BindingFragment<FragmentMyPingleBinding>(R.layout.fragment_my_pingle) {
    private val viewModel by viewModels<MyPingleViewModel>()
    private lateinit var myPingleAdapter: MyPingleAdatper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        myPingleAdapter = MyPingleAdatper(
            navigateToMapList = ::navigateToMapList,
            showChatModalDialogFragment = ::showChatModalDialogFragment,
            showDeleteModalDialogFragment = ::showDeleteModalDialogFragment
        )
        binding.rvMyPingle.adapter = myPingleAdapter
    }

    private fun addListeners() {

    }

    private fun navigateToMapList() {
        navigateToFragment<MainListFragment>()
    }

    private fun showChatModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { },
            clickTextBtn = { }
        ).show(childFragmentManager, "")
    }

    private fun showDeleteModalDialogFragment(pingleEntity: PingleEntity) {
        AllModalDialogFragment(
            title = stringOf(R.string.map_cancel_modal_title),
            detail = stringOf(R.string.map_cancel_modal_detail),
            buttonText = stringOf(R.string.map_cancel_modal_button_text),
            textButtonText = stringOf(R.string.map_cancel_modal_text_button_text),
            clickBtn = { },
            clickTextBtn = { }
        ).show(childFragmentManager, "")
    }
}
