package org.sopt.pingle.presentation.ui.participant

import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityParticipantBinding
import org.sopt.pingle.util.base.BindingActivity

class ParticipantActivity :
    BindingActivity<ActivityParticipantBinding>(R.layout.activity_participant) {
    private val participantViewModel by viewModels<ParticipantViewModel>()
    private val participantAdapter: ParticipantAdapter by lazy {
        ParticipantAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.rvParticipant.adapter = participantAdapter
        participantAdapter.submitList(participantViewModel.mockParticipantList)
    }

    private fun addListeners() {
        binding.includeParticipantTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }
}
