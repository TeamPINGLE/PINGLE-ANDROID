package org.sopt.pingle.util.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import org.sopt.pingle.R
import org.sopt.pingle.databinding.SearchPingleBinding

@SuppressLint("CustomViewStyleable")
class PingleSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val _binding: SearchPingleBinding
    val binding get() = _binding

    init {
        _binding = SearchPingleBinding.inflate(LayoutInflater.from(context), this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PingleSearch)
        try {
            initView(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun initView(typedArray: TypedArray) {
        with(_binding) {
            typedArray.apply {
                etSearchPingleEditText.hint = getString(R.styleable.PingleSearch_pingleSearchHint)
            }

            etSearchPingleEditText.doAfterTextChanged { text ->
                if (!etSearchPingleEditText.hasFocus()) etSearchPingleEditText.requestFocus()
                _binding.ivSearchPingleSearch.visibility =
                    if (text.isNullOrEmpty()) View.VISIBLE else View.GONE
                _binding.ivSearchPingleClear.visibility =
                    if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            etSearchPingleEditText.setOnEditorActionListener { textView, _, _ ->
                textView.text.isEmpty()
            }

            ivSearchPingleClear.setOnClickListener {
                _binding.etSearchPingleEditText.text.clear()
            }
        }
    }
}
