package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanCategoryBinding
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.util.base.BindingFragment
import timber.log.Timber

class PlanCategoryFragment :
    BindingFragment<FragmentPlanCategoryBinding>(R.layout.fragment_plan_category) {
    private val viewModel by activityViewModels<PlanViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.planViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.selectedCategory.flowWithLifecycle(lifecycle).onEach {
            Timber.tag("observe:categoryType").d(viewModel.selectedCategory.value?.name.toString())
        }.launchIn(lifecycleScope)

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
