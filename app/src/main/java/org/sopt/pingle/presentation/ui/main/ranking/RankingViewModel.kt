package org.sopt.pingle.presentation.ui.main.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.domain.model.RankingEntity
import org.sopt.pingle.domain.usecase.GetRankingUseCase
import org.sopt.pingle.util.view.UiState

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val pingleLocalDataSource: PingleLocalDataSource,
    private val getRankingUseCase: GetRankingUseCase
) : ViewModel() {
    private val _rankingState = MutableStateFlow<UiState<RankingEntity>>(UiState.Empty)
    val rankingState get() = _rankingState.asStateFlow()

    fun getRanking() {
        viewModelScope.launch {
            getRankingUseCase(teamId = pingleLocalDataSource.groupId.toLong()).onSuccess { rankingEntity ->
                _rankingState.value = UiState.Success(rankingEntity)
            }.onFailure { throwable ->
                _rankingState.value = UiState.Error(throwable.message)
            }
        }
    }
}
