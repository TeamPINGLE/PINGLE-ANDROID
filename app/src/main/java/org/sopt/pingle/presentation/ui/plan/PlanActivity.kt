package org.sopt.pingle.presentation.ui.plan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.main.planannouncement.PlanAnnouncementActivity
import org.sopt.pingle.presentation.ui.plan.plancategory.PlanCategoryFragment
import org.sopt.pingle.presentation.ui.plan.plandatetime.PlanDateTimeFragment
import org.sopt.pingle.presentation.ui.plan.planlocation.PlanLocationFragment
import org.sopt.pingle.presentation.ui.plan.planopenchatting.PlanOpenChattingFragment
import org.sopt.pingle.presentation.ui.plan.planrecruitment.PlanRecruitmentFragment
import org.sopt.pingle.presentation.ui.plan.plansummaryconfirmation.PlanSummaryConfirmationFragment
import org.sopt.pingle.presentation.ui.plan.plantitle.PlanTitleFragment
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class PlanActivity : BindingActivity<ActivityPlanBinding>(R.layout.activity_plan) {
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = planViewModel

        setPlanFragmentStateAdapter()
        initView()
        addListeners()
        collectData()
        onBackPressedBtn()
    }

    private fun initView() {
        with(binding.planProgress) {
            min = 1f
            max = fragmentList.size.toFloat()
        }
    }

    private fun setPlanFragmentStateAdapter() {
        fragmentList = ArrayList()
        fragmentList.apply {
            add(PlanCategoryFragment())
            add(PlanTitleFragment())
            add(PlanDateTimeFragment())
            add(PlanLocationFragment())
            add(PlanRecruitmentFragment())
            add(PlanOpenChattingFragment())
            add(PlanSummaryConfirmationFragment())
        }

        val adapter = PlanFragmentStateAdapter(fragmentList, this)
        with(binding.vpPlan) {
            this.adapter = adapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        planViewModel.setCurrentPage(position)
                    }
                })
        }
    }

    private fun addListeners() {
        binding.btnPlan.setOnClickListener {
            when (binding.vpPlan.currentItem) {
                fragmentList.size - 1 -> {
                    planViewModel.postPlanMeeting()
                }
                else -> {
                    binding.vpPlan.currentItem++
                }
            }
        }
        binding.toolbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            navigateToPreviousPage()
        }
        binding.tvPlanClose.setOnClickListener {
            showExitModalDialogFragment()
        }
    }

    private fun collectData() {
        planViewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            binding.planProgress.progress = currentPage.toFloat() + 1f
            when (currentPage) {
                fragmentList.size - 1 -> {
                    binding.btnPlan.text = getString(R.string.plan_pingle)
                    binding.layoutClose.visibility = View.INVISIBLE
                }
                // TODO 다른 다음으로 스트링과 합치기
                else -> {
                    binding.btnPlan.text = getString(R.string.plan_next)
                    binding.layoutClose.visibility = View.VISIBLE
                }
            }
        }.launchIn(lifecycleScope)

        planViewModel.planMeetingState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> navigateToHome()
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun showExitModalDialogFragment() {
        AllModalDialogFragment(
            title = getString(R.string.plan_exit_modal_dialog_title),
            detail = getString(R.string.plan_exit_modal_dialog_detail),
            buttonText = getString(R.string.plan_exit_modal_dialog_btn_text),
            textButtonText = getString(R.string.plan_exit_modal_dialog_text_btn_text),
            clickBtn = {},
            clickTextBtn = { finish() }
        ).show(supportFragmentManager, EXIT_MODAL)
    }

    private fun navigateToPreviousPage() {
        when (binding.vpPlan.currentItem) {
            0 -> {
                navigateToPlanAnnouncement()
            }

            else -> {
                binding.vpPlan.currentItem--
            }
        }
    }

    private fun navigateToPlanAnnouncement() {
        Intent(this, PlanAnnouncementActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun navigateToHome() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            finish()
        }
    }

    private fun onBackPressedBtn() {
        onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousPage()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    companion object {
        private const val EXIT_MODAL = "exitModal"
    }
}
