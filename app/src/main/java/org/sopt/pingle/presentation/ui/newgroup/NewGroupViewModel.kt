package org.sopt.pingle.presentation.ui.newgroup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.pingle.presentation.ui.plan.PlanViewModel

@HiltViewModel
class NewGroupViewModel @Inject constructor() : ViewModel() {
    private val _currentPage = MutableStateFlow(PlanViewModel.FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }
}
