package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _location = MutableStateFlow<String?>(null)
    val location get() = _location.asStateFlow()

    private val _x = MutableStateFlow<Double?>(null)
    val locationX get() = _x.asStateFlow()

    private val _y = MutableStateFlow<Double?>(null)
    val locationY get() = _y.asStateFlow()

    private val _address = MutableStateFlow<String?>(null)
    val address get() = _address.asStateFlow()

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setPlanLocation(location: String, locationX: Double, locationY: Double, address: String) {
        _location.value = location
        _x.value = locationX
        _y.value = locationY
        _address.value = address
    }
    companion object {
        const val FIRST_PAGE_POSITION = 0
    }
}
