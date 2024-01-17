package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.context.colorOf
import org.sopt.pingle.util.convertToCalenderDetail

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val context: Context,
    private val navigateToMapList: () -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showDeleteModalDialogFragment: (MyPingleEntity) -> Unit,
    private val viewClickListener: (ConstraintLayout) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: MyPingleEntity) {
        with(binding) {
            myPingleItem = item
            badgeMyPingleCategory.setBadgeCategoryType(CategoryType.fromString(item.category))
            tvMyPingleName.setTextColor(
                context.colorOf(CategoryType.fromString(item.category).textColor)
            )
            tvMyPingleCalenderDetail.text =
                convertToCalenderDetail(
                    date = item.date,
                    startAt = item.startAt,
                    endAt = item.endAt
                )
            tvMyPingleCalenderRecruitmentDetail.text = context.getString(
                R.string.my_pingle_participant,
                item.curParticipants,
                item.maxParticipants
            )

            if (item.isOwner) {
                ivMyPingleOwner.visibility = View.VISIBLE
                tvMyPingleMenuTrash.setTextColor(context.getColor(R.color.g_08))
                ivMyPingleMenuTrash.setImageResource(R.drawable.ic_my_trash_inactivated_20)
            } else {
                ivMyPingleOwner.visibility = View.INVISIBLE
            }

            if (item.dDay.isEmpty()) {
                tvMyPingleDay.visibility = View.INVISIBLE
            } else if (item.dDay == DONE) {
                tvMyPingleDay.text = DONE
                tvMyPingleDay.setTextColor(context.getColor(R.color.g_10))
                ViewCompat.setBackgroundTintList(
                    tvMyPingleDay,
                    ContextCompat.getColorStateList(context, R.color.g_07)
                )
            } else {
                tvMyPingleDay.text = item.dDay
                tvMyPingleDay.visibility = View.VISIBLE
            }

            // 클릭 리스너
            viewClickListener(binding.layoutMyPingleMenu)

            layoutMyPingleRecruitment.setOnClickListener {
                navigateToMapList
            }

            ivMyPingleEdit.setOnClickListener {
                val currentVisibility = layoutMyPingleMenu.visibility
                if (currentVisibility == View.VISIBLE) {
                    layoutMyPingleMenu.visibility = View.INVISIBLE
                } else {
                    layoutMyPingleMenu.visibility = View.VISIBLE
                }
            }

            tvMyPingleMenuChat.setOnClickListener {
                navigateToWebViewWithChatLink(item.chatLink)
            }

            tvMyPingleMenuTrash.setOnClickListener {
                showDeleteModalDialogFragment(item)
            }

            binding.root.setOnClickListener {
                layoutMyPingleMenu.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        const val DONE = "Done"
    }
}
