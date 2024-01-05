package org.sopt.pingle.presentation.ui.joingroup

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupCodeBinding
import org.sopt.pingle.util.base.BindingActivity

@AndroidEntryPoint
class JoinGroupCodeActivity :
    BindingActivity<ActivityJoinGroupCodeBinding>(R.layout.activity_join_group_code) {
    private val joinViewModel by viewModels<JoinViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addObservers()
    }

    private fun addObservers() {
        joinViewModel.joinGroupData.observe(this) { joinGroupData ->
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
    }
}
