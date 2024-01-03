package org.sopt.pingle.util.base

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("image")
fun setImageResource(imageView: ImageView, resId: Int) {
    val drawable = ContextCompat.getDrawable(imageView.context, resId)
    imageView.setImageDrawable(drawable)
}
