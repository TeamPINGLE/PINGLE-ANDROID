package org.sopt.pingle.presentation.ui.participant

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityParticipantBinding
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleCardTop.Companion.MEETING_ID
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class ParticipantActivity :
    BindingActivity<ActivityParticipantBinding>(R.layout.activity_participant) {
    private val participantViewModel by viewModels<ParticipantViewModel>()
    private val participantAdapter: ParticipantAdapter by lazy { ParticipantAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onDestroy() {
        binding.rvParticipant.adapter = null
        super.onDestroy()
    }

    private fun initLayout() {
        binding.rvParticipant.adapter = participantAdapter
        participantViewModel.getParticipantList(intent.getLongExtra(MEETING_ID, DEFAULT_VALUE))
    }

    private fun addListeners() {
        binding.includeParticipantTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            finish()
        }
    }

    private fun collectData() {
        participantViewModel.participantListState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    participantAdapter.submitList(uiState.data.participants)
                }

                is UiState.Empty -> {
                    participantAdapter.submitList(null)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        const val DEFAULT_VALUE = -1L
    }
}
