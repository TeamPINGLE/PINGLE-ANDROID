package org.sopt.pingle.presentation.ui.joingroup

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityJoinGroupCodeBinding
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.CustomSnackbar

@AndroidEntryPoint
class JoinGroupCodeActivity :
    BindingActivity<ActivityJoinGroupCodeBinding>(R.layout.activity_join_group_code) {
    private val joinViewModel by viewModels<JoinViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addObservers()
    }

    private fun initLayout() {
    }

    private fun addObservers() {
        joinViewModel.run {
            joinGroupData.observe(this@JoinGroupCodeActivity) {
                binding.run {
                    tvJoinGroupCodeGroupType.text = it.keyword
                    tvJoinGroupCodeGroupName.text = it.name
                    tvJoinGroupCodeMeetingCount.text =
                        getString(R.string.join_group_code_meeting_count, it.meetingCount)
                    tvJoinGroupCodeParticipantCount.text =
                        getString(R.string.join_group_code_participant_count, it.participantCount)
                }
            }
        }
    }
}
