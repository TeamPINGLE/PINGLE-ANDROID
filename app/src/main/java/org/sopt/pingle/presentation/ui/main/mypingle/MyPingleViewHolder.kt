package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.context.colorOf

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val context: Context,
    private val navigateToMapList: () -> Unit,
    private val showChatModalDialogFragment: (PingleEntity) -> Unit,
    private val showDeleteModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBinding(item: MyPingleEntity) {
        with(binding) {
            myPingleItem = item
            badgeMyPingleCategory.setBadgeCategoryType(CategoryType.fromString(item.category))
            tvMyPingleName.setTextColor(
                context.colorOf(CategoryType.fromString(item.category).textColor)
            )
            tvMyPingleCalenderDetail.text = (item.date + "|" + item.startAt + "~" + item.endAt)
            tvMyPingleCalenderRecruitmentDetail.text =
                (item.curParticipants + "/" + item.maxParticipants + "명")
        }


        with(binding) {
            layoutMyPingleRecruitment.setOnClickListener {
                navigateToMapList
            }

            ivMyPingleEdit.setOnClickListener {
                layoutMyPingleMenu.visibility = View.VISIBLE
            }

            tvMyPingleMenuChat.setOnClickListener {
                showChatModalDialogFragment
            }

            tvMyPingleMenuTrash.setOnClickListener {
                showDeleteModalDialogFragment
            }
        }
    }

    private fun convertTimeFormat(time: String): String =
        time.substring(
            TIME_START_INDEX, TIME_END_INDEX
        )

    companion object {
        const val TIME_START_INDEX = 0
        const val TIME_END_INDEX = 5
    }
}
