package org.sopt.pingle.presentation.ui.joingroup

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupSearchBinding
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.hideKeyboard

class JoinGroupSearchActivity :
    BindingActivity<ActivityJoinGroupSearchBinding>(R.layout.activity_join_group_search) {
    private val joinGroupViewModel by viewModels<JoinViewModel>()
    private lateinit var joinGroupSearchAdapter: JoinGroupSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        joinGroupSearchAdapter = JoinGroupSearchAdapter(::deleteOldPosition)
        binding.rvJoinGroupSearch.adapter = joinGroupSearchAdapter

        joinGroupSearchAdapter.submitList(joinGroupViewModel.mockJoinGroupSearchData)
    }

    private fun addListeners() {
        binding.ivJoinGroupSearchIcon.setOnClickListener {
            checkListExist()
            hideKeyboard(binding.etJoinGroupSearch)
        }

        binding.etJoinGroupSearch.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    checkListExist()
                    hideKeyboard(binding.etJoinGroupSearch)
                    return@OnKeyListener true
                }
                true
            }
        )
    }

    private fun checkListExist() {
        with(binding) {
            if (joinGroupViewModel.checkJoinGroupSearchIsEmpty()) {
                rvJoinGroupSearch.visibility = View.INVISIBLE
                tvJoinGroupSearchEmpty.visibility = View.VISIBLE
            } else {
                rvJoinGroupSearch.visibility = View.VISIBLE
                tvJoinGroupSearchCreate.visibility = View.INVISIBLE
            }
        }
    }

    private fun deleteOldPosition(position: Int) {
        joinGroupViewModel.updateJoinGroupSearchList(position)
    }

    override fun onDestroy() {
        binding.rvJoinGroupSearch.adapter = null
        super.onDestroy()
    }
}
