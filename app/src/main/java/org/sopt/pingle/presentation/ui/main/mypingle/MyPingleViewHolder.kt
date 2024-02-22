package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.view.DateTimeUtils
import org.sopt.pingle.util.context.colorOf
import org.sopt.pingle.util.context.navigateToWebView
import org.sopt.pingle.util.context.stringOf
import org.sopt.pingle.util.view.setBackgroundTint

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val context: Context,
    private val showCancelModalDialogFragment: (MyPingleEntity) -> Unit,
    private val showDeleteModalDialogFragment: (MyPingleEntity) -> Unit,
    private val updateMyPingleListSelectedPosition: (Int) -> Unit,
    private val clearMyPingleListSelection: () -> Unit,
    private val navigateToParticipation: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.ivMyPingleEdit.setOnClickListener {
            updateMyPingleListSelectedPosition(bindingAdapterPosition)
        }

        binding.root.setOnClickListener {
            clearMyPingleListSelection()
        }
    }

    fun onBind(myPingleEntity: MyPingleEntity) {
        with(binding) {
            this.myPingleEntity = myPingleEntity
            badgeMyPingleCategory.setBadgeCategoryType(CategoryType.fromString(myPingleEntity.category))
            tvMyPingleName.setTextColor(
                context.colorOf(CategoryType.fromString(myPingleEntity.category).textColor)
            )
            tvMyPingleCalenderDetail.text =
                DateTimeUtils.convertToCalenderDetail(
                    date = myPingleEntity.date,
                    startAt = myPingleEntity.startAt,
                    endAt = myPingleEntity.endAt
                )
            tvMyPingleCalenderRecruitmentDetail.text = context.getString(
                R.string.my_pingle_participant,
                myPingleEntity.curParticipants,
                myPingleEntity.maxParticipants
            )

            if (myPingleEntity.isOwner) {
                ivMyPingleOwner.visibility = View.VISIBLE
                tvMyPingleMenuTrash.text = context.stringOf(R.string.my_pingle_delete)
                layoutMyPingleMenuTrash.setOnClickListener {
                    showDeleteModalDialogFragment(myPingleEntity)
                }
            } else {
                ivMyPingleOwner.visibility = View.INVISIBLE
                tvMyPingleMenuTrash.text = context.stringOf(R.string.my_pingle_cancel)
                layoutMyPingleMenuTrash.setOnClickListener {
                    showCancelModalDialogFragment(myPingleEntity)
                }
            }

            when (myPingleEntity.dDay) {
                EMPTY -> {
                    ivMyPingleEdit.visibility = View.VISIBLE
                    tvMyPingleDay.visibility = View.INVISIBLE
                }

                DONE -> {
                    ivMyPingleEdit.visibility = View.INVISIBLE
                    tvMyPingleDay.visibility = View.VISIBLE
                    tvMyPingleDay.text = DONE
                    tvMyPingleDay.setTextColor(context.colorOf(R.color.g_10))
                    tvMyPingleDay.setBackgroundTint(R.color.g_07)
                }

                else -> {
                    ivMyPingleEdit.visibility = View.VISIBLE
                    tvMyPingleDay.visibility = View.VISIBLE
                    tvMyPingleDay.text = myPingleEntity.dDay
                    tvMyPingleDay.setTextColor(context.colorOf(R.color.black))
                    tvMyPingleDay.setBackgroundTint(R.color.white)
                }
            }

            layoutMyPingleMenuChat.setOnClickListener {
                context.startActivity(context.navigateToWebView(myPingleEntity.chatLink))
            }

            binding.layoutMyPingleRecruitment.setOnClickListener {
                navigateToParticipation(myPingleEntity.id.toLong())
            }
        }
    }

    companion object {
        const val DONE = "Done"
        const val EMPTY = ""
    }
}
