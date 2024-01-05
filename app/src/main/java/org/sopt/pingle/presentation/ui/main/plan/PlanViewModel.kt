package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
