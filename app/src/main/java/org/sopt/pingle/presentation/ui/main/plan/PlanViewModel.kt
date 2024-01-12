package org.sopt.pingle.presentation.ui.main.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.domain.model.PlanMeetingEntity
import org.sopt.pingle.domain.model.UserInfoEntity
import org.sopt.pingle.domain.usecase.GetPlanLocationListUseCase
import org.sopt.pingle.domain.usecase.GetUserInfoUseCase
import org.sopt.pingle.domain.usecase.PostPlanMeetingUseCase
import org.sopt.pingle.presentation.type.CategoryType
import org.sopt.pingle.presentation.type.PlanType
import org.sopt.pingle.util.combineAll
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val getPlanLocationListUseCase: GetPlanLocationListUseCase,
    private val postPlanMeetingUseCase: PostPlanMeetingUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
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

    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory get() = _selectedCategory.asStateFlow()

    private val _selectedRecruitment = MutableStateFlow<String?>("1")
    val selectedRecruitment get() = _selectedRecruitment.asStateFlow()

    private val _planLocationListState =
        MutableSharedFlow<UiState<List<PlanLocationEntity>>>()
    val planLocationListState get() = _planLocationListState.asSharedFlow()

    private val _planLocationList = MutableStateFlow<List<PlanLocationEntity>>(emptyList())

    private val _selectedLocation = MutableStateFlow<PlanLocationEntity?>(null)
    val selectedLocation get() = _selectedLocation.asStateFlow()

    private val _planMeetingState = MutableSharedFlow<UiState<Unit?>>()
    val planMeetingState get() = _planMeetingState.asSharedFlow()

    private val _userInfoState = MutableStateFlow<UiState<UserInfoEntity>>(UiState.Empty)
    val userInfoState get() = _userInfoState.asStateFlow()

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

    fun getPlanLocationList(searchWord: String) {
        viewModelScope.launch {
            _planLocationListState.emit(UiState.Loading)
            _planLocationList.value = emptyList()
            _selectedLocation.value = null
            runCatching {
                getPlanLocationListUseCase(searchWord).collect() { planLocationList ->
                    when (planLocationList.isEmpty()) {
                        true -> {
                            _planLocationListState.emit(UiState.Empty)
                        }

                        false -> {
                            _planLocationList.value = planLocationList
                            _planLocationListState.emit(UiState.Success(planLocationList))
                        }
                    }
                }
            }.onFailure { exception: Throwable ->
                _planLocationListState.emit(UiState.Error(exception.message))
            }
        }
    }

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
        _selectedLocation.value =
            if (getIsSelected(position)) _planLocationList.value[position] else null
        oldPosition = position
    }

    private fun setIsSelected(position: Int) {
        _planLocationList.value[position].isSelected.set(!_planLocationList.value[position].isSelected.get())
    }

    private fun getIsSelected(position: Int) = _planLocationList.value[position].isSelected.get()

    fun postPlanMeeting() {
        viewModelScope.launch {
            _planMeetingState.emit(UiState.Loading)
            runCatching {
                _selectedCategory.value?.let { selectedCategory ->
                    _selectedLocation.value?.let { selectedLocation ->
                        _selectedRecruitment.value?.let { selectedRecruitment ->
                            postPlanMeetingUseCase(
                                teamId = TEAM_ID,
                                planMeetingEntity = PlanMeetingEntity(
                                    category = selectedCategory.name,
                                    name = planTitle.value,
                                    startAt = planDate.value + " " + startTime.value,
                                    endAt = planDate.value + " " + endTime.value,
                                    x = selectedLocation.x,
                                    y = selectedLocation.y,
                                    address = selectedLocation.address,
                                    roadAddress = selectedLocation.roadAddress,
                                    location = selectedLocation.location,
                                    maxParticipants = selectedRecruitment.toInt(),
                                    chatLink = planOpenChattingLink.value
                                )
                            ).collect() { data ->
                                _planMeetingState.emit(UiState.Success(data))
                            }
                        }
                    }
                }
            }.onFailure { exception: Throwable ->
                _planMeetingState.emit(UiState.Error(exception.message))
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UiState.Loading
            runCatching {
                getUserInfoUseCase().collect() { userInfo ->
                    _userInfoState.value = UiState.Success(userInfo)
                }
            }.onFailure { exception: Throwable ->
                _userInfoState.value = UiState.Error(exception.message)
            }
        }
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
        const val DEFAULT_OLD_POSITION = -1
        const val INVALID_RECRUIT = "1"
        const val TEAM_ID = 1
    }
}
