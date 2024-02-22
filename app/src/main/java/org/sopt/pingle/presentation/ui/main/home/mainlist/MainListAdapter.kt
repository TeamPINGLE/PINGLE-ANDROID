package org.sopt.pingle.presentation.ui.main.home.mainlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemMainListPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MainListAdapter(
    private val navigateToParticipant: (Long) -> Unit,
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showMapJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapCancelModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapDeleteModalDialogFragment: (PingleEntity) -> Unit
) : ListAdapter<PingleEntity, MainListViewHolder>(
    ItemDiffCallback<PingleEntity>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder =
        MainListViewHolder(
            binding = ItemMainListPingleCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context = parent.context,
            navigateToParticipant = navigateToParticipant,
            navigateToWebViewWithChatLink = navigateToWebViewWithChatLink,
            showMapJoinModalDialogFragment = showMapJoinModalDialogFragment,
            showMapCancelModalDialogFragment = showMapCancelModalDialogFragment,
            showMapDeleteModalDialogFragment = showMapDeleteModalDialogFragment
        )

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.onBind(pingleEntity = currentList[position])
    }
}