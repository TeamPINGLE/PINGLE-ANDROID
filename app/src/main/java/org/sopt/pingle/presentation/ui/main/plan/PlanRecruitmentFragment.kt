package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopt.pingle.R
import org.sopt.pingle.databinding.FragmentPlanRecruitmentBinding
import org.sopt.pingle.util.base.BindingFragment

class PlanRecruitmentFragment :
    BindingFragment<FragmentPlanRecruitmentBinding>(R.layout.fragment_plan_recruitment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
