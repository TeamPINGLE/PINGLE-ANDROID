package org.sopt.pingle.presentation.ui.joingroup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupCodeBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class JoinGroupCodeActivity :
    BindingActivity<ActivityJoinGroupCodeBinding>(R.layout.activity_join_group_code) {
    private val viewModel by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.joinViewModel = viewModel

        addListeners()
        addObservers()
    }

    private fun addListeners() {
        binding.btnJoinGroupCodeNext.setOnClickListener {
            navigateToJoinGroupSuccess()
        }
    }

    private fun addObservers() {
        viewModel.joinGroupData.observe(this) { joinGroupData ->
            with(binding) {
                tvJoinGroupCodeTag.text = joinGroupData.keyword
                tvJoinGroupCodeGroupName.text = joinGroupData.name
                tvJoinGroupCodeMeetingCount.text =
                    getString(R.string.join_group_code_meeting_count, joinGroupData.meetingCount)
                tvJoinGroupCodeParticipantCount.text =
                    getString(
                        R.string.join_group_code_participant_count,
                        joinGroupData.participantCount
                    )
            }
        }

        viewModel.joinGroupCode.observe(this) { editText ->
            binding.btnJoinGroupCodeNext.isEnabled = editText.isNotEmpty()
        }
    }

    private fun navigateToJoinGroupSuccess() {
        Intent(this, JoinGroupSuccessActivity::class.java).apply {
            // TODO 서버통신시 group name 가져와서 전달하기
            putExtra(GROUP_NAME, viewModel.joinGroupData.value?.name)
            startActivity(this)
        }
    }

    companion object {
        const val GROUP_NAME = "groupName"
    }
}
