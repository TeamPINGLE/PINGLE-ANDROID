package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMainListPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.PingleCardType

class MainListViewHolder(
    private val binding: ItemMainListPingleCardBinding,
    private val context: Context,
    private val navigateToParticipant: (Long) -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showPingleJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showPingleCancelModalDialogFragment: (PingleEntity) -> Unit,
    private val showPingleDeleteModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        with(binding) {
            ivMainListPingleCardBottomArrowUp.setOnClickListener {
                setCardExpandable(isExpanded = false)
            }

            ivMainListPingleCardBottomArrowDown.setOnClickListener {
                setCardExpandable(isExpanded = true)
            }
        }
    }

    private fun setCardExpandable(isExpanded: Boolean) {
        with(binding) {
            ivMainListPingleCardBottomArrowUp.visibility =
                if (isExpanded) View.VISIBLE else View.GONE
            pingleCardBottomMainListPingleCard.visibility =
                if (isExpanded) View.VISIBLE else View.GONE
            ivMainListPingleCardBottomArrowDown.visibility =
                if (isExpanded) View.GONE else View.VISIBLE
            layoutMainListPingleCardBottom.background = AppCompatResources.getDrawable(
                context,
                if (isExpanded) R.drawable.shape_border_radius_15 else R.drawable.shape_border_radius_8
            )
        }
    }

    fun onBind(pingleEntity: PingleEntity) {
        with(binding.pingleCardTopMainListPingleCard) {
            initLayout(pingleEntity = pingleEntity, pingleCardType = PingleCardType.MAINLIST)
            setOnParticipationStatusLayoutClick {
                navigateToParticipant(pingleEntity.id)
            }
        }

        with(binding.pingleCardBottomMainListPingleCard) {
            initLayout(pingleEntity)
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
