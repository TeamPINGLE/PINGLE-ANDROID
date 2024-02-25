package org.sopt.pingle.presentation.ui.newgroup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class NewGroupViewModel @Inject constructor() : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
