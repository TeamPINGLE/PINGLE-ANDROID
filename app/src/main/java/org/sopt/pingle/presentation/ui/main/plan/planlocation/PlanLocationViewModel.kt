package org.sopt.pingle.presentation.ui.main.plan.planlocation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.pingle.domain.model.PlanLocationEntity

class PlanLocationViewModel : ViewModel() {
    private val _planLocationList = MutableStateFlow<List<PlanLocationEntity>>(emptyList())
    private val planLocationList get() = _planLocationList.asStateFlow()

    private var oldPosition = -1
    fun updatePlanLocationList(position: Int) {
        if (oldPosition == -1 && oldPosition != position) {
            setIsSelected(true, position)
        } else if (oldPosition == position) {
            setIsSelected(false, oldPosition)
        } else {
            setIsSelected(true, position)
            setIsSelected(false, oldPosition)
        }
        oldPosition = position
    }

    fun checkIsNull(): Boolean {
        return mockPlanLocationList.isEmpty()
        // TODO return planLocationList.value.isEmpty()
    }

    private fun setIsSelected(value: Boolean, position: Int) {
        mockPlanLocationList[position].isSelected.set(value)
        // TODO 서버에서 받아올 리스트에 저장.. planLocationList.value[position].isSelected.set(value)
    }

    val mockPlanLocationList = listOf<PlanLocationEntity>(
        PlanLocationEntity(
            location = "하얀집",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        ),
        PlanLocationEntity(
            location = "하얀집2호점",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        ),
        PlanLocationEntity(
            location = "하얀집3호점",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        ),
        PlanLocationEntity(
            location = "하얀집 싫어싫어싫어",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        ),
        PlanLocationEntity(
            location = "하얀집 좋아좋아좋아",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        ),
        PlanLocationEntity(
            location = "하얀집웅시러",
            address = "서울 중구 퇴계로6길 12",
            x = 123.5,
            y = 56.7
        )
    )
}
