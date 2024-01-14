package org.sopt.pingle.presentation.ui.participant

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemParticipantBinding
import org.sopt.pingle.domain.model.ParticipantEntity

class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ParticipantEntity) {
        binding.tvParticipantName.text = item.participant
        binding.tvParticipantOwnerName.text = item.participant

        if (absoluteAdapterPosition == 0) {
            with(binding) {
                tvParticipantName.visibility = View.INVISIBLE
                tvParticipantOwner.visibility = View.VISIBLE
                tvParticipantOwnerName.visibility = View.VISIBLE
            }
        }
    }
}
