package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MyPingleAdapter(
    private val showCancelModalDialogFragment: (MyPingleEntity) -> Unit,
    private val showDeleteModalDialogFragment: (MyPingleEntity) -> Unit,
    private val updateMyPingleListSelectedPosition: (Int) -> Unit,
    private val clearMyPingleListSelection: () -> Unit,
    private val navigateToParticipation: (Long) -> Unit
) : ListAdapter<MyPingleEntity, MyPingleViewHolder>(
    ItemDiffCallback<MyPingleEntity>(
        onItemsTheSame = { old, new -> old.id == new.id },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPingleViewHolder =
        MyPingleViewHolder(
            ItemMyPingleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,

                false
            ),
            parent.context,
            showCancelModalDialogFragment,
            showDeleteModalDialogFragment,
            updateMyPingleListSelectedPosition,
            clearMyPingleListSelection,
            navigateToParticipation
        )

    override fun onBindViewHolder(holder: MyPingleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
