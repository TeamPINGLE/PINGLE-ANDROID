package org.sopt.pingle.presentation.ui.main.home.map

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMapPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity

class MapCardViewHolder(
    private val binding: ItemMapPingleCardBinding,
    private val navigateToWebView: (String) -> Unit,
    private val showMapJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapCancelModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(pinId: Long, pingleEntity: PingleEntity) {
        with(binding.cardMapPingleCard) {
            initLayout(pingleEntity)
            setPinId(pinId)
            setOnChatButtonClick {
                navigateToWebView(pingleEntity.chatLink)
            }
            setOnParticipateButtonClick {
                when (pingleEntity.isParticipating) {
                    true -> showMapCancelModalDialogFragment(pingleEntity)
                    false -> showMapJoinModalDialogFragment(pingleEntity)
                }
            }
        }
    }
}
