package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.sopt.pingle.R
import org.sopt.pingle.databinding.EditTextPingleBinding

@SuppressLint("CustomViewStyleable")
class PingleEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: EditTextPingleBinding
    private var _etText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.pingleEditText)
        try {
            initView(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun initView(typedArray: TypedArray) {
        binding = EditTextPingleBinding.inflate(LayoutInflater.from(context), this, true)
        typedArray.apply {
            val title = getString(R.styleable.pingleEditText_title)
            binding.tvTitle.text = title

            val hint = getString(R.styleable.pingleEditText_hint)
            binding.etEditText.hint = hint

            _etText = getString(R.styleable.pingleEditText_etText)
            binding.etEditText.setText(_etText)
        }
    }
}
