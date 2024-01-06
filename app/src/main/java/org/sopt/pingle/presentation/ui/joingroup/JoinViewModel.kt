package org.sopt.pingle.presentation.ui.joingroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.pingle.domain.model.JoinGroupCodeEntity

class JoinViewModel : ViewModel() {
    private val _joinGroupData = MutableLiveData<JoinGroupCodeEntity>()
    val joinGroupData = _joinGroupData

    private var _isJoinBtn = MutableLiveData(false)
    val isJoinBtn = _isJoinBtn

    init {
        _joinGroupData.value = JoinGroupCodeEntity(
            id = 1,
            keyword = "연합동아리",
            name = "SOPT",
            meetingCount = 10,
            participantCount = 200
        )
    }
}
