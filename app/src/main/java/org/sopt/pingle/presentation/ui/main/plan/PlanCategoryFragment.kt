package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanCategoryBinding
import org.sopt.pingle.util.base.BindingFragment

class PlanCategoryFragment :
    BindingFragment<FragmentPlanCategoryBinding>(R.layout.fragment_plan_category) {
    // TODO 뷰모델 추가 private val planViewModel by viewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
    }
}
