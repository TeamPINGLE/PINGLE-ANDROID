package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(1)
    val currentPage get() = _currentPage.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }
}
