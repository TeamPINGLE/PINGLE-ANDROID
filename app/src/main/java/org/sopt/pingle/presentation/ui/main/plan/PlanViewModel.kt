package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()
    private val _planDate = MutableStateFlow<String?>(null)
    val planDate get() = _planDate.asStateFlow()
    private val _selectedTimeType = MutableStateFlow<String?>(null)
    val selectedTimeType get() = _selectedTimeType.asStateFlow()

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
