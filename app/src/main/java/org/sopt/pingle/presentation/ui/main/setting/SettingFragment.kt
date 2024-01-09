package org.sopt.pingle.presentation.ui.main.setting

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.BuildConfig
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentSettingBinding
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment

@AndroidEntryPoint
class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
    }

    private fun initLayout() {
        binding.tvSettingVersionContent.text = BuildConfig.VERSION_NAME.toString()
    }

    private fun addListeners() {
        binding.tvSettingLogoutTitle.setOnClickListener {
            showLogoutDialogFragment()
        }
        binding.tvSettingWithdrawTitle.setOnClickListener {
            showWithDrawLogoutDialogFragment()
        }
    }

    private fun showLogoutDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.setting_logout_modal_title),
            detail = getString(R.string.setting_logout_modal_detail),
            buttonText = getString(R.string.setting_modal_back),
            textButtonText = getString(R.string.setting_logout_modal_btn_text),
            clickBtn = {},
            clickTextBtn = {
                // TODO 로그아웃 서버통신
            },
            onDialogClosed = {}
        ).show(parentFragmentManager, LOGOUT_MODAL)
    }

    private fun showWithDrawLogoutDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.setting_withdraw_modal_title),
            detail = getString(R.string.setting_withdraw_modal_detail),
            buttonText = getString(R.string.setting_modal_back),
            textButtonText = getString(R.string.setting_withdraw_modal_btn_text),
            clickBtn = { },
            clickTextBtn = {
                // TODO 회원탈퇴 서버통신
            },
            onDialogClosed = {}
        ).show(parentFragmentManager, WITHDRAW_MODAL)
    }

    companion object {
        private const val LOGOUT_MODAL = "logoutModal"
        private const val WITHDRAW_MODAL = "withModal"
    }
}
