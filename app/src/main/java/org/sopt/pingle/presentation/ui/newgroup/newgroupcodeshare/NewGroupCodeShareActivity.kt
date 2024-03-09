package org.sopt.pingle.presentation.ui.newgroup.newgroupcodeshare

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityNewGroupCodeShareBinding
import org.sopt.pingle.presentation.model.NewGroupModel
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.Intent.getCompatibleParcelableExtra
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.context.sharePingle
import org.sopt.pingle.util.view.copyGroupCode

@AndroidEntryPoint
class NewGroupCodeShareActivity :
    BindingActivity<ActivityNewGroupCodeShareBinding>(R.layout.activity_new_group_code_share) {
    private lateinit var newGroupModel: NewGroupModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        newGroupModel = intent.getCompatibleParcelableExtra(NEW_GROUP_CODE) ?: NewGroupModel("", "")

        with(binding) {
            etNewGroupCodeCopy.editText.setText(newGroupModel.code)
            etNewGroupCodeCopy.editText.isEnabled = false
        }
    }

    private fun addListeners() {
        with(binding) {
            includeNewGroupCodeShareTopbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener { finish() }

            etNewGroupCodeCopy.ivEditTextCopy.setOnClickListener {
                root.copyGroupCode(copyCode = newGroupModel.code)
                AmplitudeUtils.trackEvent(CLICK_CREATEGROUP_INVITE_COPY)
            }

            btnNewGroupCodeShare.setOnClickListener {
                sharePingle(
                    getString(
                        R.string.my_group_share_pingle,
                        newGroupModel.name,
                        newGroupModel.name,
                        newGroupModel.code,
                    )
                )
                AmplitudeUtils.trackEvent(CLICK_CREATEGROUP_INVITE_SHARE)
            }
        }
    }

    companion object {
        const val NEW_GROUP_CODE = "NewGroupCode"
        const val SNACKBAR_BOTTOM_MARGIN = 97
        private const val CLICK_CREATEGROUP_INVITE_COPY = "click_creategroup_invite_copy"
        private const val CLICK_CREATEGROUP_INVITE_SHARE = "click_creategroup_invite_share"
    }
}
