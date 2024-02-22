package org.sopt.pingle.presentation.ui.main.home.map

import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMapPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.PingleCardType

class MapCardViewHolder(
    private val binding: ItemMapPingleCardBinding,
    private val navigateToParticipant: (Long) -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showPingleJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showPingleCancelModalDialogFragment: (PingleEntity) -> Unit,
    private val showPingleDeleteModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(pinId: Long, pingleEntity: PingleEntity) {
        with(binding.pingleCardTopMapPingleCard) {
            initLayout(pingleEntity = pingleEntity, pingleCardType = PingleCardType.MAP)
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
                    pingleEntity.isOwner -> showPingleDeleteModalDialogFragment(pingleEntity)
                    pingleEntity.isParticipating -> showPingleCancelModalDialogFragment(pingleEntity)
                    else -> showPingleJoinModalDialogFragment(pingleEntity)
                }
            }
        }
    }
}
