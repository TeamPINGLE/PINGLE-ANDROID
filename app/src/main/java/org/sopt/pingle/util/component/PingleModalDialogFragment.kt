package org.sopt.pingle.util.component

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogPingleModalBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingDialogFragment
import org.sopt.pingle.util.fragment.colorOf

class PingleModalDialogFragment(
    private val category: CategoryType,
    private val name: String,
    private val ownerName: String,
    private val clickBtn: () -> Unit,
    private val onDialogClosed: () -> Unit = {}
) : BindingDialogFragment<DialogPingleModalBinding>(R.layout.dialog_pingle_modal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogClosed.invoke()
    }

    private fun initLayout() {
        with(binding) {
            badgePingleModalPingleInfoCategory.setBadgeCategoryType(category)
            tvPingleModalPingleInfoName.text = name
            tvPingleModalPingleInfoName.setTextColor(colorOf(category.textColor))
            tvPingleModalPingleInfoOwnerName.text = ownerName
        }
    }

    private fun addListeners() {
        binding.btnPingleModal.setOnClickListener {
            clickBtn()
            dismiss()
        }

        binding.layoutPingleModalBackground.setOnClickListener {
            dismiss()
        }
    }
}
