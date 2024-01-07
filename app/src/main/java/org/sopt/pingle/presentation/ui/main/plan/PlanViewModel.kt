package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.sopt.pingle.presentation.type.PlanType

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()
    val planTitle = MutableStateFlow("")
    private val _planDate = MutableStateFlow<String?>(null)
    val planDate get() = _planDate.asStateFlow()
    private val _selectedTimeType = MutableStateFlow<String?>(null)
    val selectedTimeType get() = _selectedTimeType.asStateFlow()
    val planOpenChattingLink = MutableStateFlow("")

    // TODO Type에 맞게 수정(현재는 내가 맡은 부분 테스트를 위해 postion 값을 조정 및 임의 지정 해놓음
    val isPlanBtnEnabled: StateFlow<Boolean> =
        combine(
            currentPage,
            planTitle,
            planOpenChattingLink
        ) { currentPage, planTitle, planOpenChattingLink ->
            (currentPage == PlanType.TITLE.position - 1 && planTitle.isNotBlank()) ||
                currentPage == 1 ||
                (currentPage == 2 && planOpenChattingLink.isNotBlank())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setPlanDate(planDate: String) {
        _planDate.value = planDate
    }

    fun setSelectedTimeType(timeType: String) {
        _selectedTimeType.value = timeType
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
