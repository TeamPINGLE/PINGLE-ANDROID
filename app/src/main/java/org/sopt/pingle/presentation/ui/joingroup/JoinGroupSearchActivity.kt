package org.sopt.pingle.presentation.ui.joingroup

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupSearchBinding
import org.sopt.pingle.presentation.ui.onboarding.OnBoardingActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.hideKeyboard
import org.sopt.pingle.util.context.navigateToWebView

class JoinGroupSearchActivity :
    BindingActivity<ActivityJoinGroupSearchBinding>(R.layout.activity_join_group_search) {
    private val viewModel by viewModels<JoinViewModel>()
    private lateinit var joinGroupSearchAdapter: JoinGroupSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.joinViewModel = viewModel

        initLayout()
        addListeners()
        addObservers()
    }

    private fun initLayout() {
        joinGroupSearchAdapter = JoinGroupSearchAdapter(::deleteOldPosition)
        binding.rvJoinGroupSearch.adapter = joinGroupSearchAdapter
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(binding.etJoinGroupSearch)
        }

        binding.ivJoinGroupSearchIcon.setOnClickListener {
            // TODO 서버통신 함수 호출
            hideKeyboard(binding.etJoinGroupSearch)
        }

        binding.etJoinGroupSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                // TODO 서버통신 함수 호출
                hideKeyboard(binding.etJoinGroupSearch)
                return@setOnKeyListener true
            }
            true
        }

        binding.tvJoinGroupSearchCreate.setOnClickListener {
            startActivity(navigateToWebView(OnBoardingActivity.NEW_GROUP_LINK))
        }

        binding.btnJoinGroupCodeNext.setOnClickListener {
            navigateToJoinGroupCode()
        }
    }

    private fun addObservers() {
        viewModel.joinGroupSearchData.flowWithLifecycle(lifecycle).onEach { joinGroupSearchList ->
            // TODO 서버통신 시 옵저빙 체크
            joinGroupSearchAdapter.submitList(joinGroupSearchList)

            binding.tvJoinGroupSearchEmpty.isVisible = joinGroupSearchList.isEmpty()
        }.launchIn(lifecycleScope)
    }

    private fun deleteOldPosition(position: Int) {
        viewModel.updateJoinGroupSearchList(position)
    }

    private fun navigateToJoinGroupCode() {
        Intent(this, JoinGroupCodeActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onDestroy() {
        binding.rvJoinGroupSearch.adapter = null
        super.onDestroy()
    }
}
