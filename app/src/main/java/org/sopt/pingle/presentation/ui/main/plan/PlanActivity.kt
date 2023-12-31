package org.sopt.pingle.presentation.ui.main.plan

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanBinding
import org.sopt.pingle.presentation.ui.main.MainActivity
import org.sopt.pingle.presentation.ui.main.plan.planlocation.PlanLocationFragment
import org.sopt.pingle.util.base.BindingActivity
import org.sopt.pingle.util.component.AllModalDialogFragment

class PlanActivity : BindingActivity<ActivityPlanBinding>(R.layout.activity_plan) {
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = planViewModel

        setPlanFragmentStateAdapter()
        initView()
        addListeners()
        collectData()
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
                // TODO 핑글 개최 api 연동
                fragmentList.size - 1 -> {}
                else -> {
                    binding.vpPlan.currentItem++
                }
            }
        }
        binding.toolbar.ivAllTopbarArrowWithTitleArrowLeft.setOnClickListener {
            when (binding.vpPlan.currentItem) {
                0 -> {
                    showExitModalDialogFragment()
                }

                else -> {
                    binding.vpPlan.currentItem--
                }
            }
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
                    binding.btnPlan.setOnClickListener {
                        navigateToMain()
                    }
                }
                // TODO 다른 다음으로 스트링과 합치기
                else -> {
                    binding.btnPlan.text = getString(R.string.plan_next)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showExitModalDialogFragment() {
        // TODO 차후에 나가기 눌렀을 때 finish() 되는지 확인
        AllModalDialogFragment(
            title = getString(R.string.plan_exit_modal_dialog_title),
            detail = getString(R.string.plan_exit_modal_dialog_detail),
            buttonText = getString(R.string.plan_exit_modal_dialog_btn_text),
            textButtonText = getString(R.string.plan_exit_modal_dialog_text_btn_text),
            clickBtn = {
                navigateToMain()
            },
            clickTextBtn = { finish() }
        ).show(supportFragmentManager, EXIT_MODAL)
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
    }

    companion object {
        private const val EXIT_MODAL = "exitModal"
    }
}
