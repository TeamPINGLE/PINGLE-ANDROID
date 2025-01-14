package org.sopt.pingle.presentation.ui.participant

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemParticipantBinding

class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(name: String) {
        with(binding) {
            (absoluteAdapterPosition == ORGANIZER_POSITION).let { isOrganizerPosition ->
                if (isOrganizerPosition) {
                    tvParticipantOwnerName.text = name
                } else {
                    tvParticipantName.text = name
                }
                tvParticipantName.visibility = if (isOrganizerPosition) View.INVISIBLE else View.VISIBLE
                tvParticipantOwner.visibility = if (isOrganizerPosition) View.VISIBLE else View.INVISIBLE
                tvParticipantOwnerName.visibility = if (isOrganizerPosition) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    companion object {
        const val ORGANIZER_POSITION = 0
    }
}
