package org.sopt.pingle.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.View
import androidx.activity.OnBackPressedCallback
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivitySearchBinding
import org.sopt.pingle.presentation.model.SearchModel
import org.sopt.pingle.presentation.ui.main.home.HomeFragment.Companion.SEARCH_MODEL
import org.sopt.pingle.util.Intent.getCompatibleParcelableExtra
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.colorOf
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
                // TODO jihyun 추후 Spannable 관련 확장함수 생성
                binding.tvSearchSearchDescription.text = getString(
                    R.string.search_description,
                    stringOf(searchDescriptionRes)
                ).let { searchDescription ->
                    SpannableString(searchDescription).apply {
                        searchDescription.indexOf(getString(searchDescriptionRes))
                            .takeIf { it != INVALID_INDEX }?.let { index ->
                                setSpan(
                                    ForegroundColorSpan(colorOf(R.color.pingle_green)),
                                    index,
                                    index + getString(searchDescriptionRes).length,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                    }
                }
            }

            binding.pingleSearchSearch.binding.etSearchPingleEditText.run {
                if (searchModel.searchWord.isNotBlank()) setText(searchModel.searchWord)
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToHome(SEARCH_CLEAR)
            }
        })
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
        private const val INVALID_INDEX = -1
        const val SEARCH_WORD = "searchWord"
        const val SEARCH_CLEAR = ""
    }
}
