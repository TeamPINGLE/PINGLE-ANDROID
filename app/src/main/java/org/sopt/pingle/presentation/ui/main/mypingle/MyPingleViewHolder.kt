package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.context.colorOf
import org.sopt.pingle.util.convertToCalenderDetail

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val context: Context,
    private val userName: String,
    private val navigateToMapList: () -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showDeleteModalDialogFragment: (PingleEntity) -> Unit
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

            if (userName == item.name) {
                tvMyPingleName.setTextColor(context.getColor(R.color.g_08))
                ivMyPingleMenuTrash.setImageResource(R.drawable.ic_my_trash_inactivated_20)
            }

            layoutMyPingleRecruitment.setOnClickListener {
                navigateToMapList
            }

            ivMyPingleEdit.setOnClickListener {
                layoutMyPingleMenu.visibility = View.VISIBLE
            }

            tvMyPingleMenuChat.setOnClickListener {
                navigateToWebViewWithChatLink(item.chatLink)
            }

            tvMyPingleMenuTrash.setOnClickListener {
                showDeleteModalDialogFragment
            }

            binding.root.setOnClickListener {
                layoutMyPingleMenu.visibility = View.INVISIBLE
            }
        }
    }
}
