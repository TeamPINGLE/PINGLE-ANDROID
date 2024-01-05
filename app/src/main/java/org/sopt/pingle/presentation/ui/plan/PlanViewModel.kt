package org.sopt.pingle.presentation.ui.plan

import androidx.lifecycle.ViewModel
import org.sopt.pingle.presentation.model.PlanModel

class PlanViewModel : ViewModel() {
    val mockPlanList = listOf<PlanModel>(
        PlanModel(
            category = "others",
            name = "개빡세게 공부",
            date = "2024년 1월 18일",
            startAt = "오후 05:00",
            endAt = "오후 10:00",
            x = 15.8,
            y = 192.4,
            address = "서울 중구 퇴계로6길 12",
            location = "하얀집",
        ),
    )
}
