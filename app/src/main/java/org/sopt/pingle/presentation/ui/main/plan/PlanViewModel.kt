package org.sopt.pingle.presentation.ui.main.plan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.PlanType
import org.sopt.pingle.util.combineAll

class PlanViewModel : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()
    val planTitle = MutableStateFlow("")
    private val _planDate = MutableStateFlow("")
    val planDate get() = _planDate.asStateFlow()
    private val _startTime = MutableStateFlow("")
    val startTime get() = _startTime.asStateFlow()
    private val _endTime = MutableStateFlow("")
    val endTime get() = _endTime.asStateFlow()
    private val _selectedTimeType = MutableStateFlow<String?>(null)

    val selectedTimeType get() = _selectedTimeType.asStateFlow()
    val planOpenChattingLink = MutableStateFlow("")
    val planSummary = MutableStateFlow("")

    private val _selectedLocation = MutableStateFlow<PlanLocationEntity?>(null)
    val selectedLocation get() = _selectedLocation.asStateFlow()

    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    private val _selectedRecruitment = MutableStateFlow<String?>("1")
    val selectedRecruitment get() = _selectedRecruitment.asStateFlow()

    val isPlanBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        selectedCategory,
        planTitle,
        planDate,
        startTime,
        endTime,
        selectedLocation,
        selectedRecruitment,
        planOpenChattingLink,
        planSummary
    ).combineAll()
        .map { values ->
            val currentPage = values[0] as Int
            val selectedCategory = values[1] as? CategoryType
            val planTitle = values[2] as String
            val planDate = values[3] as String
            val startTime = values[4] as String
            val endTime = values[5] as String
            val selectedLocation = values[6] as? PlanLocationEntity
            val selectedRecruitment = values[7] as String
            val planOpenChattingLink = values[8] as String

            (currentPage == PlanType.CATEGORY.position && selectedCategory != null) ||
                    (currentPage == PlanType.TITLE.position && planTitle.isNotBlank()) ||
                    (currentPage == PlanType.DATETIME.position && planDate.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()) ||
                    (currentPage == PlanType.LOCATION.position && selectedLocation != null) ||
                    (currentPage == PlanType.RECRUITMENT.position && selectedRecruitment.isNotBlank() && selectedRecruitment != INVALID_RECRUIT) ||
                    (currentPage == PlanType.OPENCHATTING.position && planOpenChattingLink.isNotBlank()) ||
                    (currentPage == PlanType.SUMMARY.position)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun incRecruitmentNum() {
        selectedRecruitment.value?.toInt()?.let { setSelectedRecruitment(it.plus(1).toString()) }
    }

    fun decRecruitmentNum() {
        selectedRecruitment.value?.toInt()?.let { setSelectedRecruitment(it.minus(1).toString()) }
    }

    fun setSelectedRecruitment(recruitment: String) {
        _selectedRecruitment.value = recruitment
    }

    fun setSelectedCategory(categoryType: CategoryType) {
        _selectedCategory.value = categoryType
    }

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setPlanDate(planDate: String) {
        _planDate.value = planDate
    }

    fun setStartTime(time: String) {
        _startTime.value = time
    }

    fun setEndTime(time: String) {
        _endTime.value = time
    }

    fun setSelectedTimeType(timeType: String) {
        _selectedTimeType.value = timeType
    }

    private fun setPlanLocation(position: Int) {
        _selectedLocation.value = _planLocationList.value[position]
    }

    private var oldPosition = DEFAULT_OLD_POSITION

    private val _planLocationList = MutableStateFlow<List<PlanLocationEntity>>(emptyList())
    val planLocationList get() = _planLocationList.asStateFlow()

    fun updatePlanLocationList(position: Int) {
        when (oldPosition) {
            DEFAULT_OLD_POSITION -> {
                setIsSelected(position)
            }

            position -> {
                setIsSelected(position)
                oldPosition = DEFAULT_OLD_POSITION
            }

            else -> {
                if (getIsSelected(oldPosition)) setIsSelected(oldPosition)
                setIsSelected(position)
            }
        }
        _selectedLocation.value = if(getIsSelected(position)) _planLocationList.value[position] else null
        oldPosition = position
    }


    //이전 값이 -> 초기값 + 셀렉티드 값이 있으면
    fun checkIsNull(): Boolean {
        return _planLocationList.value.isEmpty()
        // TODO return planLocationList.value.isEmpty()
    }

    private fun setIsSelected(position: Int) {
        _planLocationList.value[position].isSelected.set(!_planLocationList.value[position].isSelected.get())
    }

    private fun getIsSelected(position: Int) = _planLocationList.value[position].isSelected.get()

    init {
        _planLocationList.value = listOf(
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

    companion object {
        const val FIRST_PAGE_POSITION = 0
        const val DEFAULT_OLD_POSITION = -1
        const val INVALID_RECRUIT = "1"
    }
}
