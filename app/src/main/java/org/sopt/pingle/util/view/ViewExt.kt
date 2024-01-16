package org.sopt.pingle.util.view

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.DiffUtil

fun View.stringOf(@StringRes resId: Int) = getString(context, resId)

fun View.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

class ItemDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = onItemsTheSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        onContentsTheSame(oldItem, newItem)
}
