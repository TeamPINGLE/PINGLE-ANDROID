package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMainListPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.model.MainListPingleModel
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

    fun onBind(mainListPingleModel: MainListPingleModel) {
        setCardExpandable(mainListPingleModel.isExpanded.get())

        with(binding) {
            ivMainListPingleCardBottomArrowUp.setOnClickListener {
                mainListPingleModel.isExpanded.set(false)
            }

            ivMainListPingleCardBottomArrowDown.setOnClickListener {
                mainListPingleModel.isExpanded.set(true)
            }
        }

        with(binding.pingleCardTopMainListPingleCard) {
            initLayout(
                pingleEntity = mainListPingleModel.pingleEntity,
                pingleCardType = PingleCardType.MAINLIST
            )
            setOnParticipationStatusLayoutClick {
                navigateToParticipant(mainListPingleModel.pingleEntity.id)
            }
        }

        with(binding.pingleCardBottomMainListPingleCard) {
            initLayout(mainListPingleModel.pingleEntity)
            setOnChatButtonClick {
                navigateToWebViewWithChatLink(mainListPingleModel.pingleEntity.chatLink)
            }
            setOnParticipateButtonClick {
                when {
                    mainListPingleModel.pingleEntity.isOwner -> showPingleDeleteModalDialogFragment(
                        mainListPingleModel.pingleEntity
                    )

                    mainListPingleModel.pingleEntity.isParticipating -> showPingleCancelModalDialogFragment(
                        mainListPingleModel.pingleEntity
                    )

                    else -> showPingleJoinModalDialogFragment(mainListPingleModel.pingleEntity)
                }
            }
        }

        mainListPingleModel.isExpanded.let { isExpanded ->
            isExpanded.addOnPropertyChangedCallback(object :
                Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    setCardExpandable(isExpanded = isExpanded.get())
                }
            })
        }
    }
}
