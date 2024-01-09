package org.sopt.pingle.presentation.ui.joingroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

class JoinViewModel : ViewModel() {
    private val _joinGroupData = MutableLiveData<JoinGroupCodeEntity>()
    val joinGroupData get() = _joinGroupData

    private var _isJoinGroupCodeBtn = MutableLiveData(false)
    val isJoinGroupCodeBtn get() = _isJoinGroupCodeBtn

    val joinGroupCode = MutableLiveData<String>()

    private val _joinGroupSearchData = MutableStateFlow<List<JoinGroupSearchEntity>>(emptyList())
    val joinGroupSearchData get() = _joinGroupSearchData

    val joinGroupSearchEditText = MutableLiveData<String>("")

    private val _joinGroupSearchBtn = MutableLiveData<Boolean>(false)
    val joinGroupSearchBtn get() = _joinGroupSearchBtn

    private var oldPosition = OLD_POSITION
    fun updateJoinGroupSearchList(newPosition: Int) {
        when (oldPosition) {
            OLD_POSITION -> {
                setIsSelected(newPosition)
            }

            newPosition -> {
                setIsSelected(newPosition)
                oldPosition = OLD_POSITION
            }

            else -> {
                setIsSelected(oldPosition)
                setIsSelected(newPosition)
            }
        }
        oldPosition = newPosition
        _joinGroupSearchBtn.value = _joinGroupSearchData.value[newPosition].isSelected.get()
    }

    private fun setIsSelected(position: Int) {
        _joinGroupSearchData.value[position].isSelected.set(
            !_joinGroupSearchData.value[position].isSelected.get()
        )
    }

    init {
        _joinGroupData.value = JoinGroupCodeEntity(
            id = 0,
            keyword = "연합동아리",
            name = "SOPT",
            meetingCount = 10,
            participantCount = 200
        )

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
        private const val OLD_POSITION = -1
    }
}
