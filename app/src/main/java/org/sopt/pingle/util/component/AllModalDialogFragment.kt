package org.sopt.pingle.util.component

import android.os.Bundle
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogAllModalBinding
import org.sopt.pingle.util.base.BindingDialogFragment

class AllModalDialogFragment(
    private val title: String,
    private val detail: String,
    private val buttonText: String,
    private val textButtonText: String,
    private val clickBtn: () -> Unit,
    private val clickTextBtn: () -> Unit
) : BindingDialogFragment<DialogAllModalBinding>(R.layout.dialog_all_modal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        with(binding) {
            tvAllModalTitle.text = title
            tvAllModalDetail.text = detail
            btnAllModalButton.text = buttonText
            tvAllModalTextButton.text = textButtonText
        }
    }

    private fun addListeners() {
        binding.btnAllModalButton.setOnClickListener {
            clickBtn()
            dismiss()
        }

        binding.tvAllModalTextButton.setOnClickListener {
            clickTextBtn()
            dismiss()
        }
    }
}
