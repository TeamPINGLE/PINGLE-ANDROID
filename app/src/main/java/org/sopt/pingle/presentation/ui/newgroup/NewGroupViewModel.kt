package org.sopt.pingle.presentation.ui.newgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupCreateEntity
import org.sopt.pingle.domain.model.NewGroupKeywordEntity
import org.sopt.pingle.domain.usecase.GetNewGroupCheckNameUseCase
import org.sopt.pingle.domain.usecase.GetNewGroupKeywordsUserCase
import org.sopt.pingle.domain.usecase.PostNewGroupCreateUseCase
import org.sopt.pingle.presentation.type.NewGroupType
import org.sopt.pingle.util.flow.combineAll
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class NewGroupViewModel @Inject constructor(
    private val getNewGroupCheckNameUseCase: GetNewGroupCheckNameUseCase,
    private val getNewGroupKeywordsUserCase: GetNewGroupKeywordsUserCase,
    private val postNewGroupCreateUseCase: PostNewGroupCreateUseCase
) : ViewModel() {
    private val _currentPage = MutableStateFlow(FIRST_PAGE_POSITION)
    val currentPage get() = _currentPage.asStateFlow()

    private val _newGroupCheckNameState =
        MutableStateFlow<UiState<NewGroupCheckNameEntity>>(UiState.Empty)
    val newGroupCheckNameState get() = _newGroupCheckNameState.asStateFlow()

    private val _newGroupKeywordsState =
        MutableStateFlow<UiState<List<NewGroupKeywordEntity>>>(UiState.Empty)
    val newGroupKeywordsState get() = _newGroupKeywordsState.asStateFlow()

    private val _newGroupCreateState =
        MutableStateFlow<UiState<NewGroupCreateEntity>>(UiState.Empty)
    val newGroupCreateState get() = _newGroupCreateState.asStateFlow()

    val newGroupName = MutableStateFlow<String>("")
    val newGroupEmail = MutableStateFlow<String>("")
    val newGroupBtnCheckName = MutableStateFlow<Boolean>(false)
    val newGroupKeywordName = MutableStateFlow<String>("")
    val newGroupKeywordValue = MutableStateFlow<String>("")

    val isNewGroupBtnEnabled: StateFlow<Boolean> = listOf(
        currentPage,
        newGroupName,
        newGroupEmail,
        newGroupBtnCheckName,
        newGroupKeywordValue
    ).combineAll().map { values ->
        val currentPage = values[0] as Int
        val newGroupName = values[1] as String
        val newGroupEmail = values[2] as String
        val newGroupBtnCheckName = values[3] as Boolean
        val newGroupKeyword = values[4] as String

        (currentPage == NewGroupType.INPUT.position && newGroupName.isNotBlank() && newGroupEmail.isNotBlank() && newGroupBtnCheckName) ||
            (currentPage == NewGroupType.KEYWORD.position && newGroupKeyword.isNotBlank()) ||
            (currentPage == NewGroupType.CREATE.position)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    fun setCurrentPage(position: Int) {
        _currentPage.value = position
    }

    fun setNewGroupKeyword(keywordName: String, keywordValue: String) {
        newGroupKeywordName.value = keywordName
        newGroupKeywordValue.value = keywordValue
    }

    fun setNewGroupBtnTrue() {
        newGroupBtnCheckName.value = true
    }

    fun isEmailValid() = EMAIL_PATTERN.matches(newGroupEmail.value)

    fun getNewGroupCheckName() {
        viewModelScope.launch {
            _newGroupCheckNameState.emit(UiState.Loading)
            getNewGroupCheckNameUseCase(teamName = newGroupName.value).onSuccess { newGroupCheckNameData ->
                _newGroupCheckNameState.value = UiState.Success(newGroupCheckNameData)
            }.onFailure { throwable ->
                _newGroupCheckNameState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun getNewGroupKeywords() {
        viewModelScope.launch {
            _newGroupKeywordsState.emit(UiState.Loading)
            getNewGroupKeywordsUserCase().onSuccess { newGroupKeywordsData ->
                _newGroupKeywordsState.value = UiState.Success(newGroupKeywordsData)
            }.onFailure { throwable ->
                _newGroupKeywordsState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun postNewGroupCreate() {
        viewModelScope.launch {
            _newGroupCreateState.emit(UiState.Loading)
            postNewGroupCreateUseCase.invoke(
                requestNewGroupCreateDto = RequestNewGroupCreateDto(
                    name = newGroupName.value,
                    email = newGroupEmail.value,
                    keyword = newGroupKeywordName.value
                )
            ).onSuccess { newGroupCreateData ->
                _newGroupCreateState.value = UiState.Success(newGroupCreateData)
            }.onFailure { throwable ->
                _newGroupCreateState.value = UiState.Error(throwable.message)
            }
        }
    }

    companion object {
        const val FIRST_PAGE_POSITION = 0
        val EMAIL_PATTERN = """[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,64}""".toRegex()
    }
}
