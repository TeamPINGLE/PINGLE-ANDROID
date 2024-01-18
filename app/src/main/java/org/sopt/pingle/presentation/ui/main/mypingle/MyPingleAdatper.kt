package org.sopt.pingle.presentation.ui.main.mypingle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemMyPingleBinding
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MyPingleAdatper(
    private val context: Context,
    private val navigateToMapList: () -> Unit,
    private val showDeleteModalDialogFragment: (MyPingleEntity) -> Unit,
    private val setOldItem: (Int) -> Unit
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
            context,
            navigateToMapList,
            showDeleteModalDialogFragment,
            setOldItem
        )

    override fun onBindViewHolder(holder: MyPingleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
