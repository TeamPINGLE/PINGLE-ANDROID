package org.sopt.pingle.util.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("image")
fun setImageResource(imageView: ImageView, resId: Int) {
    val drawable = ContextCompat.getDrawable(imageView.context, resId)
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("color")
fun setTextColor(textView: TextView, resId: Int) {
    val colorRes = ContextCompat.getColor(textView.context, resId)
    textView.setTextColor(colorRes)
}

@BindingAdapter("selection")
fun setSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}
