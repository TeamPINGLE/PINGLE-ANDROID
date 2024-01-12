package org.sopt.pingle.presentation.ui.plan.plancategory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanCategoryBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.ui.plan.PlanViewModel
import org.sopt.pingle.util.base.BindingFragment

@AndroidEntryPoint
class PlanCategoryFragment :
    BindingFragment<FragmentPlanCategoryBinding>(R.layout.fragment_plan_category) {
    private val viewModel by activityViewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = viewModel

        addListeners()
    }

    private fun addListeners() {
        with(binding) {
            includePlanCategoryTypePlay.root.setOnClickListener {
                viewModel.setSelectedCategory(CategoryType.PLAY)
            }
            includePlanCategoryTypeStudy.root.setOnClickListener {
                viewModel.setSelectedCategory(CategoryType.STUDY)
            }
            includePlanCategoryTypeMulti.root.setOnClickListener {
                viewModel.setSelectedCategory(CategoryType.MULTI)
            }
            includePlanCategoryTypeOthers.root.setOnClickListener {
                viewModel.setSelectedCategory(CategoryType.OTHERS)
            }
        }
    }
}
