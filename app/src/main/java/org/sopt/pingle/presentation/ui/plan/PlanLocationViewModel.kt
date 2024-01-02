package org.sopt.pingle.presentation.ui.plan

import androidx.lifecycle.ViewModel
import org.sopt.pingle.presentation.model.PlanLocationModel

class PlanLocationViewModel : ViewModel() {
    val mockPlanLocationList = listOf<PlanLocationModel>(
        PlanLocationModel(
            location = "하얀집",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
        PlanLocationModel(
            location = "하얀집2호점",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
        PlanLocationModel(
            location = "하얀집3호점",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
        PlanLocationModel(
            location = "하얀집 싫어싫어싫어",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
        PlanLocationModel(
            location = "하얀집 좋아좋아좋아",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
        PlanLocationModel(
            location = "하얀집웅시러",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7,
        ),
    )
}
