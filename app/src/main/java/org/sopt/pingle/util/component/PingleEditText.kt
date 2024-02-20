package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import org.sopt.pingle.R
import org.sopt.pingle.databinding.EditTextPingleBinding

@SuppressLint("CustomViewStyleable")
class PingleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: EditTextPingleBinding
    val editText get() = binding.etEditText

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.edit_text_pingle,
            this,
            true
        )
        binding.view = this
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PingleEditText)
        try {
            initView(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun initView(typedArray: TypedArray) {
        typedArray.apply {
            val title = getString(R.styleable.PingleEditText_pingleEditTextTitle)
            binding.tvTitle.text = title

            val hint = getString(R.styleable.PingleEditText_pingleEditTextHint)
            binding.etEditText.hint = hint

            val maxLength = getInt(R.styleable.PingleEditText_pingleEditTextMaxLength, -1)
            if (maxLength > 0) {
                binding.etEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
            }
        }
    }
}
