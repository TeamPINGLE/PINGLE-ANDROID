package org.sopt.pingle.presentation.ui.main.home.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.pingle.databinding.ItemMapPingleCardBinding
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.util.view.ItemDiffCallback

class MapCardAdapter(
    private val navigateToWebViewWithChatLink: (String) -> Unit,
    private val showMapJoinModalDialogFragment: (PingleEntity) -> Unit,
    private val showMapCancelModalDialogFragment: (PingleEntity) -> Unit
) : ListAdapter<PingleEntity, MapCardViewHolder>(
    ItemDiffCallback<PingleEntity>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id }
    )
) {
    private var _pinId: Long = DEFAULT_VALUE
    val pinId get() = _pinId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapCardViewHolder =
        MapCardViewHolder(
            ItemMapPingleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            navigateToWebViewWithChatLink,
            showMapJoinModalDialogFragment,
            showMapCancelModalDialogFragment
        )

    override fun onBindViewHolder(holder: MapCardViewHolder, position: Int) {
        holder.onBind(pinId = _pinId, pingleEntity = currentList[position])
    }

    fun setPinId(pinId: Long) {
        this._pinId = pinId
    }

    fun clearData() {
        _pinId = DEFAULT_VALUE
        submitList(emptyList())
    }

    companion object {
        const val DEFAULT_VALUE = -1L
    }
}
