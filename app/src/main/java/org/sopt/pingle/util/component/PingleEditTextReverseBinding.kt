package org.sopt.pingle.util.component

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object PingleEditTextReverseBinding {
    @JvmStatic
    @BindingAdapter("etText")
    fun setPingleEditText(view: PingleEditText, etText: String?) {
        val old = view.editText.text.toString()
        if (old != etText) {
            view.editText.setText(etText)
        }
    }

    @JvmStatic
    @BindingAdapter("etTextAttrChanged")
    fun setPingleEditTextInverseBindingListener(
        view: PingleEditText,
        listener: InverseBindingListener?
    ) {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                listener?.onChange()
            }
        }
        view.editText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "etText", event = "etTextAttrChanged")
    fun getEtText(view: PingleEditText): String {
        return view.editText.text.toString()
    }
}
