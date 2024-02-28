package org.sopt.pingle.util.component

import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogMyGroupModalBinding
import org.sopt.pingle.util.base.BindingDialogFragment

class MyGroupModalDialogFragment(
    private val title: String,
    private val buttonText: String,
    private val textButtonText: String,
    private val clickBtn: () -> Unit,
    private val clickTextBtn: () -> Unit,
    private val onDialogClosed: () -> Unit = {}
) : BindingDialogFragment<DialogMyGroupModalBinding>(R.layout.dialog_my_group_modal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogClosed()
    }

    private fun initLayout() {
        with(binding) {
            tvMyGroupModalTitle.text = title
            btnMyGroupModalButton.text = buttonText
            tvMyGroupModalTextButton.apply {
                text = textButtonText
                paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    private fun addListeners() {
        binding.btnMyGroupModalButton.setOnClickListener {
            clickBtn()
            dismiss()
        }

        binding.tvMyGroupModalTextButton.setOnClickListener {
            clickTextBtn()
            dismiss()
        }

        binding.layoutMyGroupModalBackground.setOnClickListener {
            dismiss()
        }
    }
}