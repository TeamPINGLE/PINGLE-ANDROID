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
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.presentation.ui.participant.ParticipantActivity
import org.sopt.pingle.util.base.BindingFragment
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.fragment.navigateToWebView
import org.sopt.pingle.util.view.UiState
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

    private fun initLayout() {
        with(binding) {
            tvMoreVersionDetail.text = BuildConfig.VERSION_NAME
            tvMoreMyGroupContent.text = moreViewModel.getGroupName()
        }
        moreViewModel.getUserInfo()
    }

    private fun addListeners() {
        binding.tvMoreContactTitle.setOnClickListener {
            startActivity(navigateToWebView(CONTACT))
        }

        binding.tvMoreNoticeTitle.setOnClickListener {
            startActivity(navigateToWebView(NOTICE))
        }

        binding.tvMoreLogoutTitle.setOnClickListener {
            showLogoutDialogFragment()
        }

        binding.tvMoreWithdrawTitle.setOnClickListener {
            showWithDrawLogoutDialogFragment()
        }
        binding.ivMoreMoveToMyGroup.setOnClickListener {
            startActivity(Intent(requireContext(), ParticipantActivity::class.java))
        }
    }

    private fun collectData() {
        moreViewModel.logoutState.flowWithLifecycle(lifecycle).onEach { logoutState ->
            when (logoutState) {
                is UiState.Success -> {
                    moveToSign()
                }

                is UiState.Error -> {
                    Timber.d("로그아웃 실패")
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        moreViewModel.withDrawState.flowWithLifecycle(lifecycle).onEach { withDrawState ->
            when (withDrawState) {
                is UiState.Success -> {
                    moveToSign()
                }

                is UiState.Error -> {
                    Timber.d("로그아웃 실패")
                }

                else -> {}
            }
        }.launchIn(lifecycleScope)

        moreViewModel.userInfoState.flowWithLifecycle(lifecycle).onEach { userInfoState ->
            when (userInfoState) {
                is UiState.Success -> {
                    binding.tvMoreNickname.text = userInfoState.data.name
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun moveToSign() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showLogoutDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.setting_logout_modal_title),
            detail = getString(R.string.setting_logout_modal_detail),
            buttonText = getString(R.string.setting_modal_back),
            textButtonText = getString(R.string.setting_logout_modal_btn_text),
            clickBtn = {},
            clickTextBtn = {
                kakaoAuthService.logoutKakao(moreViewModel::logout)
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
                kakaoAuthService.withdrawKakao(moreViewModel::withDraw)
            },
            onDialogClosed = {}
        ).show(parentFragmentManager, WITHDRAW_MODAL)
    }

    companion object {
        private const val LOGOUT_MODAL = "logoutModal"
        private const val WITHDRAW_MODAL = "withModal"
        private const val CONTACT =
            "https://pinglepingle.notion.site/585c13c92e1842c7ada334e78b731303?pvs=4"
        private const val NOTICE =
            "https://pinglepingle.notion.site/38d504b943a4479695b7ca9206c7b732?pvs=4"
    }
}
