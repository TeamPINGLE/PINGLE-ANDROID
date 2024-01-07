package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanCategoryBinding
import org.sopt.pingle.util.base.BindingFragment

class PlanCategoryFragment :
    BindingFragment<FragmentPlanCategoryBinding>(R.layout.fragment_plan_category) {
    private val planViewModel by viewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = planViewModel
        binding.lifecycleOwner = this

        initLayout()
        addListeners()
    }

    private fun initLayout() {
    }

    private fun addListeners() {
        with(binding) {
            includePlanCategoryTypePlay.root.setOnClickListener {
            }
            includePlanCategoryTypeStudy.root.setOnClickListener {
            }
            includePlanCategoryTypeMulti.root.setOnClickListener {
            }
            includePlanCategoryTypeOthers.root.setOnClickListener {
            }
        }
    }
}
