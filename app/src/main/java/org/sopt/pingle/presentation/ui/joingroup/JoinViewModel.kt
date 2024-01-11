package org.sopt.pingle.presentation.ui.joingroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.domain.usecase.GetJoinGroupInfoUseCase
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val getJoinGroupInfoUseCase: GetJoinGroupInfoUseCase
) : ViewModel() {
    private val _joinGroupCodeUiState =
        MutableStateFlow<UiState<JoinGroupInfoEntity>>(UiState.Empty)
    val joinGroupCodeUiState get() = _joinGroupCodeUiState.asStateFlow()

    private var _isJoinGroupCodeBtn = MutableLiveData(false)
    val isJoinGroupCodeBtn get() = _isJoinGroupCodeBtn

    val joinGroupCode = MutableLiveData<String>()

    private val _joinGroupSearchData = MutableStateFlow<List<JoinGroupSearchEntity>>(emptyList())
    val joinGroupSearchData get() = _joinGroupSearchData

    private val _selectedJoinGroup = MutableStateFlow<JoinGroupSearchEntity?>(null)
    val selectedJoinGroup get() = _selectedJoinGroup.asStateFlow()

    val joinGroupSearchEditText = MutableLiveData<String>("")

    private var oldPosition = DEFAULT_OLD_POSITION
    fun updateJoinGroupSearchList(newPosition: Int) {
        when (oldPosition) {
            DEFAULT_OLD_POSITION -> setIsSelected(newPosition)

            newPosition -> {
                setIsSelected(newPosition)
                oldPosition = DEFAULT_OLD_POSITION
            }

            else -> {
                if (getIsSelected(oldPosition)) setIsSelected(oldPosition)
                setIsSelected(newPosition)
            }
        }
        _selectedJoinGroup.value =
            if (getIsSelected(newPosition)) _joinGroupSearchData.value[newPosition] else null
        oldPosition = newPosition
    }

    private fun setIsSelected(position: Int) {
        _joinGroupSearchData.value[position].isSelected.set(
            !_joinGroupSearchData.value[position].isSelected.get()
        )
    }

    fun getJoinGroupInfo(teamId: Int) {
        _joinGroupCodeUiState.value = UiState.Loading
        viewModelScope.launch {
            _joinGroupCodeUiState.value = UiState.Loading
            runCatching {
                getJoinGroupInfoUseCase.invoke(teamId = teamId).collect { joinGroupInfo ->
                    _joinGroupCodeUiState.value = UiState.Success(joinGroupInfo)
                }
            }.onFailure {
                _joinGroupCodeUiState.value = UiState.Error(it.message)
            }
        }
    }

    private fun getIsSelected(position: Int) = _joinGroupSearchData.value[position].isSelected.get()

    init {

        _joinGroupSearchData.value = listOf(
            JoinGroupSearchEntity(
                id = 0,
                keyword = "연합동아리",
                name = "SOPT1"
            ),
            JoinGroupSearchEntity(
                id = 1,
                keyword = "연합동아리",
                name = "SOPT2"
            ),
            JoinGroupSearchEntity(
                id = 2,
                keyword = "연합동아리",
                name = "SOPT3"
            ),
            JoinGroupSearchEntity(
                id = 3,
                keyword = "연합동아리",
                name = "SOPT4"
            ),
            JoinGroupSearchEntity(
                id = 4,
                keyword = "연합동아리",
                name = "SOPT5"
            )
        )
    }

    companion object {
        private const val DEFAULT_OLD_POSITION = -1
    }
}
