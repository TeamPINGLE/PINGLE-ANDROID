package org.sopt.pingle.presentation.ui.main.plan

import android.os.Bundle
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityPlanBinding
import org.sopt.pingle.util.base.BindingActivity

class PlanActivity : BindingActivity<ActivityPlanBinding>(R.layout.activity_plan) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)
    }
}
