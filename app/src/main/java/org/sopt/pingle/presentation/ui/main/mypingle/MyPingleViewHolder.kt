package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.context.colorOf
import org.sopt.pingle.util.context.navigateToWebView
import org.sopt.pingle.util.convertToCalenderDetail

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val context: Context,
    private val showDeleteModalDialogFragment: (MyPingleEntity) -> Unit,
    private val updateMyPingleListSelectedPosition: (Int) -> Unit,
    private val clearMyPingleListSelection: () -> Unit
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
                convertToCalenderDetail(
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
                tvMyPingleMenuTrash.setTextColor(context.colorOf(R.color.g_08))
                ivMyPingleMenuTrash.setImageResource(R.drawable.ic_my_trash_inactivated_20)
            } else {
                ivMyPingleOwner.visibility = View.INVISIBLE
            }

            if (myPingleEntity.dDay.isEmpty()) {
                tvMyPingleDay.visibility = View.INVISIBLE
            } else if (myPingleEntity.dDay == DONE) {
                tvMyPingleDay.text = DONE
                tvMyPingleDay.setTextColor(context.colorOf(R.color.g_10))
                ViewCompat.setBackgroundTintList(
                    tvMyPingleDay,
                    ContextCompat.getColorStateList(context, R.color.g_07)
                )
                ivMyPingleEdit.visibility = View.INVISIBLE
                layoutMyPingleMenu.visibility = View.INVISIBLE
            } else {
                tvMyPingleDay.text = myPingleEntity.dDay
                tvMyPingleDay.visibility = View.VISIBLE
                ivMyPingleEdit.visibility = View.VISIBLE
            }

            layoutMyPingleMenuChat.setOnClickListener {
                context.startActivity(context.navigateToWebView(myPingleEntity.chatLink))
            }

            layoutMyPingleMenuTrash.setOnClickListener {
                showDeleteModalDialogFragment(myPingleEntity)
            }
        }
    }

    companion object {
        const val DONE = "Done"
    }
}
