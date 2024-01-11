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
import org.sopt.pingle.domain.model.RequestJoinGroupCodeEntity
import org.sopt.pingle.domain.model.ResponseJoinGroupCodeEntity
import org.sopt.pingle.domain.usecase.GetJoinGroupInfoUseCase
import org.sopt.pingle.domain.usecase.PostJoinGroupCodeUseCase
import org.sopt.pingle.util.view.UiState
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val getJoinGroupInfoUseCase: GetJoinGroupInfoUseCase,
    private val postJoinGroupCodeUseCase: PostJoinGroupCodeUseCase
) : ViewModel() {
    private val _selectedJoinGroup = MutableStateFlow<JoinGroupSearchEntity?>(null)
    val selectedJoinGroup get() = _selectedJoinGroup.asStateFlow()

    private val _joinGroupSearchData = MutableStateFlow<List<JoinGroupSearchEntity>>(emptyList())
    val joinGroupSearchData get() = _joinGroupSearchData

    private val _joinGroupCodeState =
        MutableStateFlow<UiState<JoinGroupInfoEntity>>(UiState.Empty)
    val joinGroupCodeState get() = _joinGroupCodeState.asStateFlow()

    private var _isJoinGroupCodeBtn = MutableLiveData(false)
    val isJoinGroupCodeBtn get() = _isJoinGroupCodeBtn

    private var _joinGroupCode =
        MutableStateFlow<UiState<ResponseJoinGroupCodeEntity>>(UiState.Empty)
    val joinGroupCode get() = _joinGroupCode

    val joinGroupCodeEditText = MutableLiveData<String>()

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

    private fun getIsSelected(position: Int) = _joinGroupSearchData.value[position].isSelected.get()

    fun getJoinGroupInfo(teamId: Int) {
        _joinGroupCodeState.value = UiState.Loading
        viewModelScope.launch {
            _joinGroupCodeState.value = UiState.Loading
            runCatching {
                getJoinGroupInfoUseCase.invoke(teamId = teamId).collect { joinGroupInfo ->
                    _joinGroupCodeState.value = UiState.Success(joinGroupInfo)
                }
            }.onFailure {
                _joinGroupCodeState.value = UiState.Error(it.message)
            }
        }
    }

    fun postJoinGroupCode(teamId: Int, code: RequestJoinGroupCodeEntity) {
        _joinGroupCode.value = UiState.Loading
        viewModelScope.launch {
            runCatching {
                postJoinGroupCodeUseCase.invoke(teamId = teamId, code = code)
                    .collect { joinGroupCode ->
                        _joinGroupCode.value = UiState.Success(joinGroupCode)
                    }
            }.onFailure {
                _joinGroupCode.value = UiState.Error(it.message)
            }
        }
    }

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
