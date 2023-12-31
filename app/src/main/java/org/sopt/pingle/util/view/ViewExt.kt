package org.sopt.pingle.util.view

import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.snackbar.Snackbar

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit
) {
    var isClickable = true
    setOnClickListener { view ->
        if (isClickable) {
            isClickable = false
            block(view)
            view.postDelayed({
                isClickable = true
            }, delay)
        }
    }
}

fun View.showSnackBar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration).show()
}

fun View.stringOf(@StringRes resId: Int) = getString(context, resId)

fun View.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

fun View.drawableOf(@DrawableRes resId: Int) = ContextCompat.getDrawable(context, resId)

class ItemDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = onItemsTheSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        onContentsTheSame(oldItem, newItem)
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
