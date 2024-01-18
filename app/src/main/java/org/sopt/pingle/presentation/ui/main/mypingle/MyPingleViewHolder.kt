package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
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
    private val setOldItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            setOldItem(bindingAdapterPosition)
        }
    }

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
                tvMyPingleMenuTrash.setTextColor(context.colorOf(R.color.g_08))
                ivMyPingleMenuTrash.setImageResource(R.drawable.ic_my_trash_inactivated_20)
            } else {
                ivMyPingleOwner.visibility = View.INVISIBLE
            }

            if (item.dDay.isEmpty()) {
                tvMyPingleDay.visibility = View.INVISIBLE
            } else if (item.dDay == DONE) {
                tvMyPingleDay.text = DONE
                tvMyPingleDay.setTextColor(context.colorOf(R.color.g_10))
                ViewCompat.setBackgroundTintList(
                    tvMyPingleDay,
                    ContextCompat.getColorStateList(context, R.color.g_07)
                )
                ivMyPingleEdit.visibility = View.INVISIBLE
                layoutMyPingleMenu.visibility = View.INVISIBLE
            } else {
                tvMyPingleDay.text = item.dDay
                tvMyPingleDay.visibility = View.VISIBLE
                ivMyPingleEdit.visibility = View.VISIBLE
            }

            ivMyPingleEdit.setOnClickListener {
                val currentVisibility = layoutMyPingleMenu.visibility
                if (currentVisibility == View.VISIBLE) {
                    layoutMyPingleMenu.visibility = View.INVISIBLE
                } else {
                    layoutMyPingleMenu.visibility = View.VISIBLE
                }
            }

            layoutMyPingleMenuChat.setOnClickListener {
                context.startActivity(context.navigateToWebView(item.chatLink))
            }

            layoutMyPingleMenuTrash.setOnClickListener {
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
