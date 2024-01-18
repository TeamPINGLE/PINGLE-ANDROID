package org.sopt.pingle.presentation.ui.joingroup

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupSearchBinding
import org.sopt.pingle.presentation.ui.onboarding.OnBoardingActivity
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.hideKeyboard
import org.sopt.pingle.util.context.navigateToWebView
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

@AndroidEntryPoint
class JoinGroupSearchActivity :
    BindingActivity<ActivityJoinGroupSearchBinding>(R.layout.activity_join_group_search) {
    private val viewModel by viewModels<JoinViewModel>()
    private lateinit var joinGroupSearchAdapter: JoinGroupSearchAdapter
    private var teamId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.joinViewModel = viewModel

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        joinGroupSearchAdapter = JoinGroupSearchAdapter(::deleteOldPosition)
        binding.rvJoinGroupSearch.adapter = joinGroupSearchAdapter
    }

    private fun addListeners() {
        binding.includeJoinGroupSearchTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }

        binding.root.setOnClickListener {
            hideKeyboard(binding.etJoinGroupSearch)
        }

        binding.ivJoinGroupSearchIcon.setOnClickListener {
            if (binding.etJoinGroupSearch.text.isNotBlank()) viewModel.getJoinGroupSearch(binding.etJoinGroupSearch.text.toString())
            hideKeyboard(binding.etJoinGroupSearch)
        }

        binding.etJoinGroupSearch.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                if (binding.etJoinGroupSearch.text.isNotBlank()) viewModel.getJoinGroupSearch(binding.etJoinGroupSearch.text.toString())
                hideKeyboard(binding.etJoinGroupSearch)
                return@setOnKeyListener true
            }
            false
        }

        binding.tvJoinGroupSearchCreate.setOnClickListener {
            startActivity(navigateToWebView(OnBoardingActivity.NEW_GROUP_LINK))
        }

        binding.btnJoinGroupCodeNext.setOnClickListener {
            navigateToJoinGroupCode()
        }
    }

    private fun collectData() {
        viewModel.joinGroupSearchState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    joinGroupSearchAdapter.submitList(uiState.data)
                    joinGroupSearchAdapter.currentList
                    binding.tvJoinGroupSearchEmpty.visibility = View.INVISIBLE
                }

                is UiState.Error -> Timber.tag(JoinGroupCodeActivity.JOIN_GROUP_CODE_ACTIVITY)
                    .d(uiState.message)

                is UiState.Loading -> Timber.tag(JoinGroupCodeActivity.JOIN_GROUP_CODE_ACTIVITY).d(
                    JoinGroupCodeActivity.LOADING
                )

                is UiState.Empty -> {
                    joinGroupSearchAdapter.submitList(null)
                    binding.tvJoinGroupSearchEmpty.visibility = View.VISIBLE
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.selectedJoinGroup.flowWithLifecycle(lifecycle).onEach { selectedJoinGroup ->
            binding.btnJoinGroupCodeNext.isEnabled = selectedJoinGroup != null
            if (selectedJoinGroup != null) {
                teamId = selectedJoinGroup.id
            }
        }.launchIn(lifecycleScope)
    }

    private fun deleteOldPosition(position: Int) {
        viewModel.updateJoinGroupSearchList(position)
    }

    private fun navigateToJoinGroupCode() {
        Intent(this, JoinGroupCodeActivity::class.java).apply {
            putExtra(TEAM_ID, teamId)
            startActivity(this)
        }
    }

    override fun onDestroy() {
        binding.rvJoinGroupSearch.adapter = null
        super.onDestroy()
    }

    companion object {
        const val TEAM_ID = "teamId"
    }
}
