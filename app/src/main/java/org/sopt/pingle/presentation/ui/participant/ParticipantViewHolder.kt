package org.sopt.pingle.presentation.ui.participant

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemParticipantBinding

class ParticipantViewHolder(private val binding: ItemParticipantBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        with(binding) {
            tvParticipantName.text = item
            tvParticipantOwnerName.text = item

            val isDefaultPosition = absoluteAdapterPosition == DEFAULT_POSITION
            tvParticipantName.visibility = if (isDefaultPosition) View.INVISIBLE else View.VISIBLE
            tvParticipantOwner.visibility = if (isDefaultPosition) View.VISIBLE else View.INVISIBLE
            tvParticipantOwnerName.visibility = if (isDefaultPosition) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object {
        const val DEFAULT_POSITION = 0
    }
}
