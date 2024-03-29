package org.sopt.pingle.presentation.ui.main.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.BuildConfig
import org.sopt.pingle.R
import org.sopt.pingle.data.service.KakaoAuthService
import org.sopt.pingle.databinding.FragmentMoreBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.presentation.ui.mygroup.MyGroupActivity
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity.Companion.FROM_ACTIVITY
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.fragment.stringOf
import org.sopt.pingle.util.view.UiState
import org.sopt.pingle.util.view.setOnSingleClickListener
import timber.log.Timber

@AndroidEntryPoint
class MoreFragment : BindingFragment<FragmentMoreBinding>(R.layout.fragment_more) {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    private val moreViewModel: MoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        addListeners()
        collectData()
    }

    override fun onResume() {
        super.onResume()
        binding.tvMoreMyGroupContent.text = moreViewModel.getGroupName()
    }

    private fun initLayout() {
        with(binding) {
            tvMoreVersionDetail.text = BuildConfig.VERSION_NAME
            tvMoreMyGroupContent.text = moreViewModel.getGroupName()
        }
        moreViewModel.getUserInfo()
    }

    private fun addListeners() {
        binding.tvMoreContactTitle.setOnSingleClickListener {
            startActivity(navigateToWebView(CONTACT))
        }

        binding.tvMoreNoticeTitle.setOnSingleClickListener {
            startActivity(navigateToWebView(NOTICE))
        }

        binding.tvMoreLogoutTitle.setOnClickListener {
            showLogoutDialogFragment()
        }

        binding.tvMoreWithdrawTitle.setOnClickListener {
            showWithDrawLogoutDialogFragment()
        }
        binding.layoutMoreMyGroup.setOnClickListener {
            AmplitudeUtils.trackEvent(START_MYGROUP)
            navigateToMyGroup()
        }
    }

    private fun collectData() {
        moreViewModel.logoutState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { logoutState ->
                when (logoutState) {
                    is UiState.Success -> {
                        AmplitudeUtils.trackEvent(LOGOUT_APP)
                        navigateToAuth()
                    }

                    is UiState.Error -> {
                        Timber.d(FAILURE_LOGOUT)
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        moreViewModel.withDrawState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { withDrawState ->
                when (withDrawState) {
                    is UiState.Success -> {
                        AmplitudeUtils.trackEvent(WITHDRAW_APP)
                        kakaoAuthService.withdrawKakao()
                        navigateToAuth()
                    }

                    is UiState.Error -> {
                        when (withDrawState.code) {
                            FAILURE_OWNER -> {
                                PingleSnackbar.makeSnackbar(
                                    requireView(),
                                    stringOf(R.string.more_snackbar_failure),
                                    SNACKBAR_BOTTOM_MARGIN,
                                    SnackbarType.WARNING
                                )
                            }

                            else -> Timber.d("$FAILURE_LOGOUT : ${withDrawState.message}")
                        }
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        moreViewModel.userInfoState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { userInfoState ->
                when (userInfoState) {
                    is UiState.Success -> {
                        binding.tvMoreNickname.text = userInfoState.data.name
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToAuth() {
        Intent(requireContext(), AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(FROM_ACTIVITY, MORE_FRAGMENT)
            startActivity(this)
        }
    }

    private fun navigateToMyGroup() {
        Intent(requireContext(), MyGroupActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun showLogoutDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.setting_logout_modal_title),
            detail = getString(R.string.setting_logout_modal_detail),
            buttonText = getString(R.string.setting_modal_back),
            textButtonText = getString(R.string.setting_logout_modal_btn_text),
            clickBtn = { },
            clickTextBtn = {
                kakaoAuthService.logoutKakao(moreViewModel::logout)
            },
            onDialogClosed = { }
        ).show(parentFragmentManager, LOGOUT_MODAL)
    }

    private fun showWithDrawLogoutDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.setting_withdraw_modal_title),
            detail = getString(R.string.setting_withdraw_modal_detail),
            buttonText = getString(R.string.setting_modal_back),
            textButtonText = getString(R.string.setting_withdraw_modal_btn_text),
            clickBtn = { },
            clickTextBtn = { moreViewModel.withDraw() },
            onDialogClosed = { }
        ).show(parentFragmentManager, WITHDRAW_MODAL)
    }

    companion object {
        private const val FAILURE_OWNER = 400
        private const val SNACKBAR_BOTTOM_MARGIN = 76
        private const val FAILURE_LOGOUT = "로그아웃 실패"
        private const val LOGOUT_MODAL = "logoutModal"
        private const val WITHDRAW_MODAL = "withModal"
        private const val CONTACT =
            "https://pinglepingle.notion.site/585c13c92e1842c7ada334e78b731303?pvs=4"
        private const val NOTICE =
            "https://pinglepingle.notion.site/38d504b943a4479695b7ca9206c7b732?pvs=4"

        private const val LOGOUT_APP = "logout_app"
        private const val WITHDRAW_APP = "withdraw_app"

        private const val START_MYGROUP = "start_mygroup"

        const val MORE_FRAGMENT = "MoreFragment"
    }
}
