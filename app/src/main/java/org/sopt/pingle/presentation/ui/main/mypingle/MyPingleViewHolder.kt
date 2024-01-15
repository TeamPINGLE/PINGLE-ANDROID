package org.sopt.pingle.presentation.ui.main.mypingle

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.model.PingleEntity

class MyPingleViewHolder(
    private val binding: ItemMyPingleBinding,
    private val navigateToMapList: () -> Unit,
    private val showChatModalDialogFragment: (PingleEntity) -> Unit,
    private val showDeleteModalDialogFragment: (PingleEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun onBinding(item: MyPingleEntity) {
        binding.myPingleItem = item

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
}