package org.sopt.pingle.presentation.ui.mygroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.MyGroupEntity
import org.sopt.pingle.domain.usecase.GetMyGroupListUseCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class MyGroupViewModel @Inject constructor(
    private val localStorgae: PingleLocalDataSource,
    private val getMyGroupListUseCase: GetMyGroupListUseCase
) : ViewModel() {
    private val _myGroupListState = MutableStateFlow<UiState<List<MyGroupEntity>>>(UiState.Empty)
    val myGroupListState get() = _myGroupListState.asStateFlow()

    private var _filteredGroupList = MutableStateFlow<List<MyGroupEntity>>(emptyList())
    val filteredGroupList get() = _filteredGroupList

    private var _selectedMyGroup = MutableStateFlow<MyGroupEntity?>(null)
    val selectedMyGroup get() = _selectedMyGroup

    private var _myGroupList = MutableStateFlow<List<MyGroupEntity>>(emptyList())

    init {
        getGroupList()
    }

    private fun getGroupList() {
        viewModelScope.launch {
            getMyGroupListUseCase().onSuccess { myGroupListEntity ->
                _myGroupListState.value = UiState.Success(myGroupListEntity)
                _selectedMyGroup.value = myGroupListEntity.find { it.id == localStorgae.groupId }
                _selectedMyGroup.value?.let { selectedMyGroup ->
                    with(localStorgae) {
                        groupId = selectedMyGroup.id
                        groupName = selectedMyGroup.name
                        meetingCount = selectedMyGroup.meetingCount
                        participantCount = selectedMyGroup.participantCount
                    }
                }
                _myGroupList.value = myGroupListEntity
                _filteredGroupList.value = myGroupListEntity.filterNot { it == selectedMyGroup.value }
            }.onFailure { throwable ->
                _myGroupListState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun changeGroupList(clickedEntity: MyGroupEntity) {
        clickedEntity.let { clickedEntity ->
            with(localStorgae) {
                groupId = clickedEntity.id
                groupName = clickedEntity.name
                meetingCount = clickedEntity.meetingCount
                participantCount = clickedEntity.participantCount
            }
        }
        _selectedMyGroup.value = clickedEntity
        _filteredGroupList.value = _myGroupList.value.filterNot { it == selectedMyGroup.value }
    }

    fun getMyGroupIsOwner(): Boolean = _selectedMyGroup.value?.isOwner ?: false

    fun getGroupName(): String = _selectedMyGroup.value?.name.orEmpty()

    fun getGroupMeetingCount(): String = _selectedMyGroup.value?.meetingCount.orEmpty()

    fun getGroupParticipantCount(): String = _selectedMyGroup.value?.participantCount.orEmpty()

    fun getGroupCode(): String = _selectedMyGroup.value?.code.orEmpty()

    fun getGroupKeyword(): String = _selectedMyGroup.value?.keyword.orEmpty()

    val dummyGroupList = listOf<MyGroupEntity>(
        MyGroupEntity(
            id = 12341,
            name = "SOPT",
            meetingCount = "12",
            participantCount = "76",
            keyword = "연합동아리",
            isOwner = true,
            code = "soptcode"
        ),
        MyGroupEntity(
            id = 48103,
            name = "UMC",
            keyword = "연합동아리",
            meetingCount = "3",
            participantCount = "72",
            isOwner = false,
            code = "umccode"
        ),
        MyGroupEntity(
            id = 314927,
            name = "SOPT MAKERS",
            keyword = "자체기구",
            meetingCount = "114",
            participantCount = "3",
            isOwner = false,
            code = "makerscode"
        ),
        MyGroupEntity(
            id = 4915,
            name = "소나무",
            keyword = "교내동아리",
            meetingCount = "98",
            participantCount = "11",
            isOwner = false,
            code = "sonamucode"
        ),
        MyGroupEntity(
            id = 12792,
            name = "불꽃컴공",
            keyword = "학생회",
            meetingCount = "35",
            participantCount = "1123",
            isOwner = true,
            code = "donggukcode"
        ),
        MyGroupEntity(
            id = 852,
            name = "TEAM PINGLE",
            keyword = "동호회",
            meetingCount = "254",
            participantCount = "56",
            isOwner = false,
            code = "pinglecode"
        ),
        MyGroupEntity(
            id = 1,
            name = "SERVER",
            keyword = "사모임",
            meetingCount = "2",
            participantCount = "16",
            isOwner = true,
            code = "aaaaaaaaaaaa"
        )
    )
}
