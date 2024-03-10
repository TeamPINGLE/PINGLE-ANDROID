package org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityOnboardingExplanationBinding
import org.sopt.pingle.presentation.type.SnackbarType
import org.sopt.pingle.presentation.ui.auth.AuthActivity
import org.sopt.pingle.presentation.ui.auth.AuthViewModel
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.onboarding.onboarding.OnboardingActivity
import org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation.OnboardingExplanationAdapter.Companion.ONBOARDING_SIZE
import org.sopt.pingle.presentation.ui.onboarding.onboardingexplanation.OnboardingExplanationAdapter.Companion.POSITION_MINUS
import org.sopt.pingle.util.activity.FINISH_INTERVAL_TIME
import org.sopt.pingle.util.activity.INIT_BACK_PRESSED_TIME
import org.sopt.pingle.util.activity.SNACKBAR_BOTTOM_MARGIN
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.PingleSnackbar
import org.sopt.pingle.util.context.stringOf
import timber.log.Timber

@AndroidEntryPoint
class OnboardingExplanationActivity :
    BindingActivity<ActivityOnboardingExplanationBinding>(R.layout.activity_onboarding_explanation) {

    private val authViewModel by viewModels<AuthViewModel>()
    private val onboardingViewModel by viewModels<OnboardingViewModel>()
    private lateinit var onboardingAdapter: OnboardingExplanationAdapter
    private lateinit var onBackPressed: OnBackPressedCallback
    private var backPressedTime = INIT_BACK_PRESSED_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        addListeners()
        collectData()
        onBackPressedBtn()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpOnboardingExplanation.adapter = null
    }

    private fun initLayout() {
        if (authViewModel.isLocalToken()) {
            if (authViewModel.isLocalGroupId()) {
                navigateToMain()
            }
            else {
                authViewModel.getUserInfo()
                navigateToOnboarding()
            }
        }

        initAdapter()
        with(binding) {
            TabLayoutMediator(
                tlOnboardingIndicator,
                vpOnboardingExplanation
            ) { _, _ -> }.attach()
        }
    }

    private fun initAdapter() {
        runCatching {
            onboardingAdapter = OnboardingExplanationAdapter()
            with(binding.vpOnboardingExplanation) {
                this.adapter = onboardingAdapter
                registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            onboardingViewModel.setCurrentPosition(position)
                        }
                    })
            }
        }.onFailure { throwable -> Timber.e(throwable.message) }
    }

    private fun addListeners() {
        with(binding) {
            btnOnboardingExplanationNext.setOnClickListener {
                if (vpOnboardingExplanation.currentItem == OnboardingExplanationAdapter.ONBOARDING_SIZE - OnboardingExplanationAdapter.POSITION_MINUS) {
                    navigateToAuth()
                } else {
                    vpOnboardingExplanation.currentItem++
                }
            }
            tvOnboardingExplanationSkip.setOnClickListener { navigateToAuth() }
        }
    }

    private fun collectData() {
        onboardingViewModel.currentPosition.flowWithLifecycle(lifecycle).onEach { currentPosition ->
            when (currentPosition) {
                ONBOARDING_SIZE - POSITION_MINUS ->
                    binding.tvOnboardingExplanationSkip.visibility =
                        View.INVISIBLE

                else -> binding.tvOnboardingExplanationSkip.visibility = View.VISIBLE
            }
        }.launchIn(lifecycleScope)
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousPageOrExit()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    private fun navigateToAuth() {
        Intent(this, AuthActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToPreviousPageOrExit() {
        with(binding) {
            if (vpOnboardingExplanation.currentItem == OnboardingExplanationAdapter.DEFAULT_POSITION) {
                if (System.currentTimeMillis() - backPressedTime <= FINISH_INTERVAL_TIME) {
                    finish()
                } else {
                    backPressedTime = System.currentTimeMillis()
                    PingleSnackbar.makeSnackbar(
                        view = binding.root,
                        message = stringOf(R.string.all_on_back_pressed_snackbar),
                        bottomMarin = SNACKBAR_BOTTOM_MARGIN,
                        snackbarType = SnackbarType.GUIDE
                    )
                }
            } else {
                vpOnboardingExplanation.currentItem--
            }
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    private fun navigateToOnboarding() {
        Intent(this, OnboardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }
}
