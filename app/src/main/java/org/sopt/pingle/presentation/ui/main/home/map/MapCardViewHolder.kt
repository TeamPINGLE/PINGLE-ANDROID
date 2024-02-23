package org.sopt.pingle.presentation.ui.main.home.map

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMapPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity

class MapCardViewHolder(
    private val binding: ItemMapPingleCardBinding,
    private val navigateToParticipant: (Long) -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showMapJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapCancelModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapDeleteModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(pinId: Long, pingleEntity: PingleEntity) {
        with(binding.pingleCardTopMapPingleCard) {
            initLayout(pingleEntity)
            setOnParticipationStatusLayoutClick {
                navigateToParticipant(pingleEntity.id)
            }
        }

        with(binding.pingleCardBottomMapPingleCard) {
            initLayout(pingleEntity)
            setPinId(pinId)
            setOnChatButtonClick {
                navigateToWebViewWithChatLink(pingleEntity.chatLink)
            }
            setOnParticipateButtonClick {
                when {
                    pingleEntity.isOwner -> showMapDeleteModalDialogFragment(pingleEntity)
                    pingleEntity.isParticipating -> showMapCancelModalDialogFragment(pingleEntity)
                    else -> showMapJoinModalDialogFragment(pingleEntity)
                }
            }
        }
    }
}
