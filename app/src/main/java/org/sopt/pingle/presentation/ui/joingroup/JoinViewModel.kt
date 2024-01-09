package org.sopt.pingle.presentation.ui.joingroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

class JoinViewModel : ViewModel() {
    private val _joinGroupData = MutableLiveData<JoinGroupCodeEntity>()
    val joinGroupData get() = _joinGroupData

    private val _joinGroupSearchBtn = MutableLiveData(false)
    val joinGroupSearchBtn: LiveData<Boolean> get() = _joinGroupSearchBtn

    private val _joinGroupSearchEditText = MutableLiveData<String>()
    val joinGroupSearchEditText get() = _joinGroupSearchEditText

    private var _isJoinBtn = MutableLiveData(false)
    val isJoinBtn get() = _isJoinBtn

    val mockJoinGroupSearchData: List<JoinGroupSearchEntity>
    private var oldPosition = -1
    fun updateJoinGroupSearchList(newPosition: Int) {
        when {
            oldPosition == -1 -> {
                setIsSelected(true, newPosition)
            }

            oldPosition != newPosition -> {
                setIsSelected(false, oldPosition)
                setIsSelected(true, newPosition)
            }

            oldPosition == newPosition -> {
                setIsSelected(false, oldPosition)
                oldPosition = -1
            }

            else -> {
                setIsSelected(false, oldPosition)
                setIsSelected(true, newPosition)
            }
        }
        oldPosition = newPosition
    }

    fun checkJoinGroupSearchIsEmpty() = mockJoinGroupSearchData.isEmpty()

    private fun setIsSelected(boolean: Boolean, position: Int) {
        mockJoinGroupSearchData[position].isSelected.set(boolean)
        _joinGroupSearchBtn.postValue(boolean)
    }

    init {
        _joinGroupData.value = JoinGroupCodeEntity(
            id = 0,
            keyword = "연합동아리",
            name = "SOPT",
            meetingCount = 10,
            participantCount = 200
        )

        mockJoinGroupSearchData = listOf(
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
}
