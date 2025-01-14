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
import org.sopt.pingle.util.AmplitudeUtils
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.AllModalDialogFragment
import org.sopt.pingle.util.view.DateTimeUtils
import org.sopt.pingle.util.view.PingleFragmentStateAdapter
import org.sopt.pingle.util.view.UiState

@AndroidEntryPoint
class PlanActivity : BindingActivity<ActivityPlanBinding>(R.layout.activity_plan) {
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var onBackPressed: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = planViewModel

        setFragmentStateAdapter()
        initView()
        addListeners()
        collectData()
        onBackPressedBtn()
    }

    private fun initView() {
        with(binding.planProgress) {
            min = DEFAULT_PROGRESSBAR
            max = fragmentList.size.toFloat()
        }
    }

    private fun setFragmentStateAdapter() {
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

        val adapter = PingleFragmentStateAdapter(fragmentList, this)
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
                fragmentList.size - SUB_LIST_SIZE -> {
                    planViewModel.postPlanMeeting()
                    AmplitudeUtils.trackEvent(CLICK_MEETINGHOLD)
                }
                else -> binding.vpPlan.currentItem++
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
                fragmentList.size - SUB_LIST_SIZE -> {
                    binding.btnPlan.text = getString(R.string.plan_pingle)
                    binding.layoutClose.visibility = View.INVISIBLE
                }
                else -> {
                    binding.btnPlan.text = getString(R.string.plan_next)
                    binding.layoutClose.visibility = View.VISIBLE
                }
            }
        }.launchIn(lifecycleScope)

        planViewModel.planMeetingState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    trackEventCompleteMeetingHold()
                    navigateToHome()
                }
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
            clickBtn = { trackEventCancelStay() },
            clickTextBtn = { trackEventCancelOut() }
        ).show(supportFragmentManager, EXIT_MODAL)
    }

    private fun trackEventCancelStay() {
        when (planViewModel.currentPage.value) {
            FIRST_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP1_CANCEL_STAY)
            SECOND_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP2_CANCEL_STAY)
            THIRD_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP3_CANCEL_STAY)
            FOURTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP4_CANCEL_STAY)
            FIFTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP5_CANCEL_STAY)
            SIXTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP6_CANCEL_STAY)
        }
    }

    private fun trackEventCancelOut() {
        when (planViewModel.currentPage.value) {
            FIRST_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP1_CANCEL_OUT)
            SECOND_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP2_CANCEL_OUT)
            THIRD_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP3_CANCEL_OUT)
            FOURTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP4_CANCEL_OUT)
            FIFTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP5_CANCEL_OUT)
            SIXTH_PAGE -> AmplitudeUtils.trackEvent(CLICK_STEP6_CANCEL_OUT)
        }
        finish()
    }

    private fun navigateToPreviousPage() {
        when (binding.vpPlan.currentItem) {
            FIRST_PAGE -> navigateToPlanAnnouncement()
            else -> binding.vpPlan.currentItem--
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

    private fun trackEventCompleteMeetingHold() {
        AmplitudeUtils.trackEventWithProperties(
            COMPLETE_MEETINGHOLD,
            mapOf(
                CATEGORY to planViewModel.selectedCategory.value.toString(),
                NAME to planViewModel.planTitle.value,
                START_AT to DateTimeUtils.convertToAmplitudeFormat(planViewModel.planDate.value, planViewModel.startTime.value),
                END_AT to planViewModel.planDate.value + PlanViewModel.BLANK_STRING + planViewModel.endTime.value,
                ROAD_ADDRESS to (planViewModel.selectedLocation.value?.roadAddress ?: ""),
                LOCATION to (planViewModel.selectedLocation.value?.location ?: ""),
                MAX_PARTICIPANTS to planViewModel.selectedRecruitment.value
            )
        )
    }

    companion object {
        private const val EXIT_MODAL = "exitModal"
        private const val DEFAULT_PROGRESSBAR = 1f
        private const val SUB_LIST_SIZE = 1

        private const val FIRST_PAGE = 0
        private const val SECOND_PAGE = 1
        private const val THIRD_PAGE = 2
        private const val FOURTH_PAGE = 3
        private const val FIFTH_PAGE = 4
        private const val SIXTH_PAGE = 5

        private const val CLICK_MEETINGHOLD = "click_meetinghold"
        private const val COMPLETE_MEETINGHOLD = "complete_meetinghold"
        private const val CATEGORY = "category"
        private const val NAME = "name"
        private const val START_AT = "startAt"
        private const val END_AT = "endAt"
        private const val ROAD_ADDRESS = "roadAddress"
        private const val LOCATION = "location"
        private const val MAX_PARTICIPANTS = "maxParticipants"

        private const val CLICK_STEP1_CANCEL_STAY = "click_step1_cancel_stay"
        private const val CLICK_STEP2_CANCEL_STAY = "click_step2_cancel_stay"
        private const val CLICK_STEP3_CANCEL_STAY = "click_step3_cancel_stay"
        private const val CLICK_STEP4_CANCEL_STAY = "click_step4_cancel_stay"
        private const val CLICK_STEP5_CANCEL_STAY = "click_step5_cancel_stay"
        private const val CLICK_STEP6_CANCEL_STAY = "click_step6_cancel_stay"

        private const val CLICK_STEP1_CANCEL_OUT = "click_step1_cancel_out"
        private const val CLICK_STEP2_CANCEL_OUT = "click_step2_cancel_out"
        private const val CLICK_STEP3_CANCEL_OUT = "click_step3_cancel_out"
        private const val CLICK_STEP4_CANCEL_OUT = "click_step4_cancel_out"
        private const val CLICK_STEP5_CANCEL_OUT = "click_step5_cancel_out"
        private const val CLICK_STEP6_CANCEL_OUT = "click_step6_cancel_out"
    }
}
