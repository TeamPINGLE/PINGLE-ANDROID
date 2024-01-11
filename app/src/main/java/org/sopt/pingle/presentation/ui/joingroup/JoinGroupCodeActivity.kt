package org.sopt.pingle.presentation.ui.joingroup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupCodeBinding
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.view.UiState
import timber.log.Timber

@AndroidEntryPoint
class JoinGroupCodeActivity :
    BindingActivity<ActivityJoinGroupCodeBinding>(R.layout.activity_join_group_code) {
    private val viewModel by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        addObservers()
        collectData()
    }

    private fun initLayout() {
        binding.joinViewModel = viewModel
        viewModel.getJoinGroupInfo(TEAM_ID)
    }

    private fun addListeners() {
        binding.btnJoinGroupCodeNext.setOnClickListener {
//            PingleSnackbar.makeSnackbar(
//                binding.root,
//                getString(R.string.join_group_code_snackbar_message),
//                97
//            )
            navigateToJoinGroupSuccess()
        }

        binding.includeJoinGroupCodeTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }

    private fun addObservers() {
        viewModel.joinGroupCode.observe(this) { editText ->
            binding.btnJoinGroupCodeNext.isEnabled = editText.isNotEmpty()
        }
    }

    private fun collectData() {
        viewModel.joinGroupCodeUiState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    with(binding) {
                        tvJoinGroupCodeTag.text = uiState.data.keyword
                        tvJoinGroupCodeGroupName.text = uiState.data.name
                        tvJoinGroupCodeMeetingCount.text =
                            getString(
                                R.string.join_group_code_meeting_count,
                                uiState.data.meetingCount
                            )
                        tvJoinGroupCodeParticipantCount.text =
                            getString(
                                R.string.join_group_code_participant_count,
                                uiState.data.participantCount
                            )
                    }
                }

                is UiState.Error -> Timber.tag(JOIN_GROUP_CODE_ACTIVITY).d(uiState.message)

                is UiState.Loading -> Timber.tag(JOIN_GROUP_CODE_ACTIVITY).d(LOADING)

                is UiState.Empty -> Timber.tag(JOIN_GROUP_CODE_ACTIVITY).d(EMPTY)
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToJoinGroupSuccess() {
        Intent(this, JoinGroupSuccessActivity::class.java).apply {
            putExtra(GROUP_NAME, binding.tvJoinGroupCodeGroupName.text)
            startActivity(this)
        }
    }

    companion object {
        const val GROUP_NAME = "groupName"
        const val TEAM_ID = 1
        const val LOADING = "Loding"
        const val EMPTY = "Empty"
        const val JOIN_GROUP_CODE_ACTIVITY = "JoinGroupCodeActivity"
    }
}
