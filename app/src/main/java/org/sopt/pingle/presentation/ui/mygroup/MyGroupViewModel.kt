package org.sopt.pingle.presentation.ui.mygroup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.MyGroupEntity

@HiltViewModel
class MyGroupViewModel @Inject constructor(
    private val localStorgae: PingleLocalDataSource
) : ViewModel() {

    private var _filteredGroupList = MutableStateFlow<List<MyGroupEntity>>(emptyList())
    val filteredGroupList get() = _filteredGroupList

    private var _selectedMyGroup = MutableStateFlow<MyGroupEntity?>(null)
    val selectedMyGroup get() = _selectedMyGroup

    // TODO 서버통신 List

    fun getGroupList() {
        // TODO 서버통신
        selectedMyGroup.value = dummyGroupList.find { it.id == localStorgae.groupId }
        selectedMyGroup.value?.let { selectedMyGroup ->
            with(localStorgae) {
                groupId = selectedMyGroup.id
                groupName = selectedMyGroup.name
                groupKeyword = selectedMyGroup.keyword
                meetingCount = selectedMyGroup.meetingCount
                participantCount = selectedMyGroup.participantCount
                isOwner = selectedMyGroup.isOwner
                code = selectedMyGroup.code
            }
        }

        _filteredGroupList.value = dummyGroupList.filterNot { it == selectedMyGroup.value }
    }

    fun changeGroupList(clickedEntity: MyGroupEntity) {
        // _filteredGroupList의 clickedPosition에 해당하는 값을 localStorage에 저장하고,
        // _filteredGroupList의 clickedPosition에 selectedMyGroup를 저장
        // selectedMyGroup에는 localStorage에 저장된 값과 같은 GroupListEntity를 저장
        clickedEntity?.let { clickedEntity ->
            with(localStorgae) {
                groupId = clickedEntity.id
                groupName = clickedEntity.name
                groupKeyword = clickedEntity.keyword
                meetingCount = clickedEntity.meetingCount
                participantCount = clickedEntity.participantCount
                isOwner = clickedEntity.isOwner
                code = clickedEntity.code
            }
        }
        _selectedMyGroup.value = clickedEntity
        _filteredGroupList.value = dummyGroupList.filterNot { it == selectedMyGroup.value }
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