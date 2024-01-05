package org.sopt.pingle.presentation.ui.main.plan

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
import org.sopt.pingle.util.base.BindingActivity

class PlanActivity : BindingActivity<ActivityPlanBinding>(R.layout.activity_plan) {
    private val planViewModel: PlanViewModel by viewModels()
    private lateinit var fragmentList: ArrayList<Fragment>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPlanFragmentStateAdapter()
        addListeners()
        collectData()
    }

    private fun setPlanFragmentStateAdapter() {
        // TODO 차후에 나머지 개최 프로세스 fragment 추가
        fragmentList = ArrayList()
        fragmentList.apply {
            add(PlanTitleFragment())
            add(PlanDateTimeFragment())
            add(PlanOpenChattingFragment())
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
                    // TODO 나가기 확인 모달
                }

                else -> {
                    binding.vpPlan.currentItem--
                }
            }
        }
        binding.tvPlanClose.setOnClickListener {
            // TODO 나가기 확인 모달
        }
    }

    private fun collectData() {
        planViewModel.currentPage.flowWithLifecycle(lifecycle).onEach { currentPage ->
            when (currentPage) {
                fragmentList.size - 1 -> {
                    binding.btnPlan.text = getString(R.string.plan_pingle)
                }

                // TODO 다른 다음으로 스트링과 합치기
                else -> {
                    binding.btnPlan.text = getString(R.string.plan_next)
                }
            }
        }.launchIn(lifecycleScope)
    }
}
