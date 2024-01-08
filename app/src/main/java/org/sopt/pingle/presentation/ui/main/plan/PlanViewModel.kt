package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.sopt.pingle.presentation.type.CategoryType
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
    /*val isPlanBtnEnabled: StateFlow<Boolean> =
        combine(
            currentPage,
            planTitle,
            planOpenChattingLink
        ) { currentPage, planTitle, planOpenChattingLink ->
            (currentPage == PlanType.TITLE.position - 1 && planTitle.isNotBlank()) ||
                currentPage == 1 ||
                (currentPage == 2 && planOpenChattingLink.isNotBlank())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)*/

    val isPlanBtnEnabled = MutableStateFlow(true)

    private val _location = MutableStateFlow<String?>(null)
    val location get() = _location.asStateFlow()

    private val _x = MutableStateFlow<Double?>(null)
    val locationX get() = _x.asStateFlow()

    private val _y = MutableStateFlow<Double?>(null)
    val locationY get() = _y.asStateFlow()

    private val _address = MutableStateFlow<String?>(null)
    val address get() = _address.asStateFlow()

    // TODO 뷰 연결 시 버튼 활성/비활성화 로직 isPlanBtnEnabled에 추가
    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    fun setSelectedCategory(categoryType: CategoryType) {
        _selectedCategory.value = categoryType
    }

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setPlanDate(planDate: String) {
        _planDate.value = planDate
    }

    fun setSelectedTimeType(timeType: String) {
        _selectedTimeType.value = timeType
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
