package org.sopt.pingle.presentation.ui.newgroup.newgroupcodeshare

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupCodeShareBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.newgroup.NewGroupViewModel
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.PINGLE_PLAY_STORE_LINK
import org.sopt.pingle.util.context.PINGLE_SHARE_CODE
import org.sopt.pingle.util.context.copyGroupCode
import org.sopt.pingle.util.context.sharePingle
import org.sopt.pingle.util.context.stringOf

@AndroidEntryPoint
class NewGroupCodeShareActivity :
    BindingActivity<ActivityNewGroupCodeShareBinding>(R.layout.activity_new_group_code_share) {
    private val newGroupViewModel: NewGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.newGroupViewModel = newGroupViewModel

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.etNewGroupCodeCopy.editText.isEnabled = false
    }

    private fun addListeners() {
        binding.includeNewGroupCodeShareTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { finish() }

        binding.etNewGroupCodeCopy.setOnClickListener {
            copyGroupCode(copyCode = newGroupViewModel.newGroupCode.value)
            PingleSnackbar.makeSnackbar(
                view = binding.root,
                message = stringOf(R.string.my_group_snack_bar_code_copy_complete),
                bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                snackbarType = SnackbarType.GUIDE
            )
        }

        binding.btnNewGroupCodeShare.setOnClickListener {
            sharePingle(
                shareContent = getString(
                    R.string.my_group_share_pingle,
                    newGroupViewModel.newGroupName.value,
                    PINGLE_SHARE_CODE,
                    newGroupViewModel.newGroupCode.value,
                    PINGLE_PLAY_STORE_LINK
                )
            )
        }
    }

    companion object {
        const val SNACKBAR_BOTTOM_MARGIN = 97
    }
}
