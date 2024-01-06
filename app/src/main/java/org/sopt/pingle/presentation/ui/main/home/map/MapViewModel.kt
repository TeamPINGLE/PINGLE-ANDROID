package org.sopt.pingle.presentation.ui.main.home.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.presentation.type.CategoryType

class MapViewModel() : ViewModel() {
    val dummyPinList = listOf(
        PinEntity(
            id = 1,
            x = 126.9275108,
            y = 37.5262935,
            category = "PLAY",
            meetingCount = 1
        ),
        PinEntity(
            id = 2,
            x = 126.9283122,
            y = 37.5259168,
            category = "STUDY",
            meetingCount = 2
        ),
        PinEntity(
            id = 3,
            x = 126.9276423,
            y = 37.5258711,
            category = "MULTI",
            meetingCount = 1
        ),
        PinEntity(
            id = 4,
            x = 126.9286719,
            y = 37.5253629,
            category = "OTHERS",
            meetingCount = 2
        )
    )

    private val _category = MutableStateFlow<CategoryType?>(null)
    val category get() = _category.asStateFlow()

    fun setCategory(category: CategoryType?) {
        _category.value = category
    }
}
