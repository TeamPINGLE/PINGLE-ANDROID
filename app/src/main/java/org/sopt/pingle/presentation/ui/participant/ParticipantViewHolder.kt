package org.sopt.pingle.presentation.ui.participant

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemParticipantBinding

class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.tvParticipantName.text = item
        binding.tvParticipantOwnerName.text = item

        if (absoluteAdapterPosition == DEFAULT_POSITION) {
            with(binding) {
                tvParticipantName.visibility = View.INVISIBLE
                tvParticipantOwner.visibility = View.VISIBLE
                tvParticipantOwnerName.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val DEFAULT_POSITION = 0
    }
}
