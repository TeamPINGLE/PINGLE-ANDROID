package org.sopt.pingle.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivitySearchBinding
import org.sopt.pingle.presentation.model.SearchModel
import org.sopt.pingle.presentation.ui.main.home.HomeFragment.Companion.SEARCH_MODEL
import org.sopt.pingle.util.Intent.getCompatibleParcelableExtra
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.hideKeyboard
import org.sopt.pingle.util.context.stringOf

class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.pingleSearchSearch.binding.etSearchPingleEditText.requestFocus()

        (intent.getCompatibleParcelableExtra<SearchModel>(SEARCH_MODEL))?.let { searchModel ->
            with(searchModel.homeViewType) {
                binding.pingleSearchSearch.binding.etSearchPingleEditText.hint =
                    stringOf(searchHintRes)
                binding.tvSearchSearchDescription.text = stringOf(searchDescriptionRes)
            }

            binding.pingleSearchSearch.binding.etSearchPingleEditText.run {
                if (searchModel.searchWord.isNotBlank()) setText(searchModel.searchWord)
            }
        }
    }

    private fun addListeners() {
        binding.root.setOnClickListener {
            hideKeyboard(binding.pingleSearchSearch.binding.etSearchPingleEditText)
        }

        with(binding.pingleSearchSearch.binding.etSearchPingleEditText) {
            setOnKeyListener(
                View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        if (text.isNotBlank()) {
                            navigateToHome(searchWord = text.toString())
                            hideKeyboard(this)
                        }
                        return@OnKeyListener true
                    }
                    false
                }
            )
        }
    }

    private fun navigateToHome(searchWord: String) {
        Intent().apply {
            putExtra(SEARCH_WORD, searchWord)
            setResult(RESULT_OK, this)
            finish()
        }
    }

    companion object {
        private const val MAP = "지도"
        private const val MAIN_LIST = "리스트"
        const val SEARCH_WORD = "searchWord"
    }
}
