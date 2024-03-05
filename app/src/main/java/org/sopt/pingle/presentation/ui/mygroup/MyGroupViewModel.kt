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
    private val localStorage: PingleLocalDataSource,
    private val getMyGroupListUseCase: GetMyGroupListUseCase
) : ViewModel() {
    private val _myGroupListState = MutableStateFlow<UiState<List<MyGroupEntity>>>(UiState.Empty)
    val myGroupListState get() = _myGroupListState.asStateFlow()

    private var _selectedMyGroup = MutableStateFlow<MyGroupEntity?>(null)
    val selectedMyGroup get() = _selectedMyGroup

    var myGroupList: List<MyGroupEntity> = emptyList<MyGroupEntity>()

    fun getGroupList() {
        viewModelScope.launch {
            getMyGroupListUseCase().onSuccess { myGroupListEntity ->
                _myGroupListState.value = UiState.Success(myGroupListEntity)
            }.onFailure { throwable ->
                _myGroupListState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun changeMyGroupInfo(clickedEntity: MyGroupEntity, adapter: MyGroupAdapter) {
        clickedEntity.let { clickedEntity ->
            with(localStorage) {
                groupId = clickedEntity.id
                groupName = clickedEntity.name
            }
        }
        _selectedMyGroup.value = clickedEntity
        adapter.submitList(myGroupList.filterNot { it == clickedEntity })
    }

    fun getMyGroupId(): Int = localStorage.groupId

    fun setSelectedMyGroup(myGroupEntity: MyGroupEntity) {
        _selectedMyGroup.value = myGroupEntity
    }


    fun getMyGroupIsOwner(): Boolean = _selectedMyGroup.value?.isOwner ?: false

    fun getGroupName(): String = _selectedMyGroup.value?.name.orEmpty()

    fun getGroupMeetingCount(): String = _selectedMyGroup.value?.meetingCount.orEmpty()

    fun getGroupParticipantCount(): String = _selectedMyGroup.value?.participantCount.orEmpty()

    fun getGroupCode(): String = _selectedMyGroup.value?.code.orEmpty()

    fun getGroupKeyword(): String = _selectedMyGroup.value?.keyword.orEmpty()
}
