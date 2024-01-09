package org.sopt.pingle.presentation.ui.main.plan

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

    // TODO Type에 맞게 수정(현재는 내가 맡은 부분 테스트를 위해 postion 값을 조정 및 임의 지정 해놓음
    val isPlanBtn =
        listOf(currentPage, planTitle, planDate, startTime, endTime, planOpenChattingLink)
            .combineAll()

    // TODO 수정 예정, 테스트를 위해 position값 임의 설정
    val isPlanBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        planTitle,
        planDate,
        startTime,
        endTime,
        planOpenChattingLink
    ).combineAll()
        .map { values ->
            val currentPage = values[0] as Int
            val planTitle = values[1] as String
            val planDate = values[2] as String
            val startTime = values[3] as String
            val endTime = values[4] as String
            val planOpenChattingLink = values[5] as String

            (currentPage == PlanType.TITLE.position - 1 && planTitle.isNotBlank()) ||
                (currentPage == 1 && planDate.isNotBlank() && startTime.isNotBlank() && endTime.isNotBlank()) ||
                (currentPage == 2 && planOpenChattingLink.isNotBlank())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    // val isPlanBtnEnabled = MutableStateFlow(true)

    private val _selectedLocation = MutableStateFlow<PlanLocationEntity?>(null)
    val selectedLocation get() = _selectedLocation.asStateFlow()

    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    private val _selectedRecruitment = MutableStateFlow<String?>("1")
    val selectedRecruitment get() = _selectedRecruitment.asStateFlow()

    fun incRecruitmentNum() {
        var i = selectedRecruitment.value?.toInt()
        i = i!! + 1
        setSelectedRecruitment(i.toString())
    }

    fun decRecruitmentNum() {
        var i = selectedRecruitment.value?.toInt()
        i = i!! - 1
        setSelectedRecruitment(i.toString())
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

    private var oldPosition = OLD_POSITION
    companion object {
        const val FIRST_PAGE_POSITION = 0
        const val OLD_POSITION = -1
    }

    private val _planLocationList = MutableStateFlow<List<PlanLocationEntity>>(emptyList())
    val planLocationList get() = _planLocationList.asStateFlow()

    fun updatePlanLocationList(position: Int) {
        when (oldPosition){
            OLD_POSITION -> {
                setIsSelected(position)
            }
            position -> {
                setIsSelected(position)
                oldPosition = OLD_POSITION
            }
            else -> {
                _planLocationList.value[oldPosition].isSelected.set(false)
                _planLocationList.value[position].isSelected.set(true)
            }
        }
        oldPosition = position
    }

    fun checkIsNull(): Boolean {
        return _planLocationList.value.isEmpty()
        // TODO return planLocationList.value.isEmpty()
    }

    private fun setIsSelected(position: Int)  {
        _planLocationList.value[position].isSelected.set(!_planLocationList.value[position].isSelected.get())
        // TODO 서버에서 받아올 리스트에 저장.. planLocationList.value[position].isSelected.set(value)
    }

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
}
