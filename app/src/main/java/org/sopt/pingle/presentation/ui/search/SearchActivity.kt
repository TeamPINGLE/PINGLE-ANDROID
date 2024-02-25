package org.sopt.pingle.presentation.ui.search

import android.os.Bundle
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivitySearchBinding
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.hideKeyboard

class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.pingleSearchSearch.editText.requestFocus()
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(binding.pingleSearchSearch.editText)
        }
    }
}
