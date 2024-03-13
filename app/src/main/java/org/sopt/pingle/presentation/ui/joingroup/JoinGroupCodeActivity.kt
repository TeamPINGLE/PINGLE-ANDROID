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
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.hideKeyboard
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.UiState
import org.sopt.pingle.util.view.setOnSingleClickListener
import timber.log.Timber

@AndroidEntryPoint
class JoinGroupCodeActivity :
    BindingActivity<ActivityJoinGroupCodeBinding>(R.layout.activity_join_group_code) {
    private val viewModel by viewModels<JoinViewModel>()
    private var teamId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.joinViewModel = viewModel

        initLayout()
        addListeners()
        collectData()
    }

    private fun initLayout() {
        teamId = intent.getIntExtra(TEAM_ID, -1)
        viewModel.joinGroupInfoState(teamId)
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(binding.etJoinGroupCodeInvitation)
        }

        binding.btnJoinGroupCodeNext.setOnSingleClickListener {
            viewModel.joinGroupCodeState(
                teamId = teamId,
                joinGroupEntity = JoinGroupCodeEntity(viewModel.joinGroupCodeEditText.value)
            )

            AmplitudeUtils.trackEvent(CLICK_EXISTINGGROUP_ENTER)
        }

        binding.includeJoinGroupCodeTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        viewModel.joinGroupInfoState.flowWithLifecycle(lifecycle).onEach { uiState ->
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

        viewModel.joinGroupCodeState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    navigateToJoinGroupSuccess()
                    AmplitudeUtils.trackEventWithProperty(
                        COMPLETE_EXISTINGGROUP,
                        KEYWORD,
                        binding.tvJoinGroupCodeTag.text
                    )
                }

                is UiState.Error -> {
                    when (uiState.message) {
                        CODE_409 -> {
                            PingleSnackbar.makeSnackbar(
                                view = binding.root,
                                message = stringOf(R.string.join_group_code_snackbar_guide_message),
                                bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                                snackbarType = SnackbarType.GUIDE
                            )
                        }

                        else -> {
                            PingleSnackbar.makeSnackbar(
                                view = binding.root,
                                message = stringOf(R.string.join_group_code_snackbar_warning_message),
                                bottomMarin = SNACKBAR_BOTTOM_MARGIN
                            )
                        }
                    }
                }

                is UiState.Loading -> Timber.tag(JOIN_GROUP_CODE_ACTIVITY).d(LOADING)

                is UiState.Empty -> Timber.tag(JOIN_GROUP_CODE_ACTIVITY).d(EMPTY)
            }
        }.launchIn(lifecycleScope)

        viewModel.joinGroupCodeEditText.flowWithLifecycle(lifecycle).onEach { editText ->
            binding.btnJoinGroupCodeNext.isEnabled = editText.isNotEmpty()
        }.launchIn(lifecycleScope)
    }

    private fun navigateToJoinGroupSuccess() {
        Intent(this, JoinGroupSuccessActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(GROUP_NAME, binding.tvJoinGroupCodeGroupName.text)
            startActivity(this)
        }
    }

    companion object {
        const val TEAM_ID = "teamId"
        const val GROUP_NAME = "groupName"
        const val LOADING = "Loading"
        const val EMPTY = "Empty"
        const val JOIN_GROUP_CODE_ACTIVITY = "JoinGroupCodeActivity"
        const val SNACKBAR_BOTTOM_MARGIN = 97
        const val CODE_409 = 409.toString()
        private const val CLICK_EXISTINGGROUP_ENTER = "click_existinggroup_enter"
        private const val COMPLETE_EXISTINGGROUP = "complete_existinggroup"
        private const val KEYWORD = "keyword"
    }
}
