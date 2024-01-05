package org.sopt.pingle.presentation.ui.main.home.map

import android.os.Bundle
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.DialogMapModalBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingDialogFragment
import org.sopt.pingle.util.fragment.colorOf

class MapModalDialogFragment(
    private val category: CategoryType,
    private val name: String,
    private val ownerName: String,
    private val clickBtn: () -> Unit
) : BindingDialogFragment<DialogMapModalBinding>(R.layout.dialog_map_modal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        with(binding) {
            badgeMapModalPingleInfoCategory.setBadgeCategoryType(category)
            tvMapModalPingleInfoName.text = name
            tvMapModalPingleInfoName.setTextColor(colorOf(category.textColor))
            tvMapModalPingleInfoOwnerName.text = ownerName
        }
    }

    private fun addListeners() {
        binding.btnMapModal.setOnClickListener {
            clickBtn()
            dismiss()
        }
    }
}
