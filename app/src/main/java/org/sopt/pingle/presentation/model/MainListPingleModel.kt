package org.sopt.pingle.presentation.model

import androidx.databinding.ObservableBoolean
import org.sopt.pingle.domain.model.PingleEntity

data class MainListPingleModel(
    val pingleEntity: PingleEntity,
    var isExpanded: ObservableBoolean = ObservableBoolean(false)
) {
    fun updateMainListPingleModel() = this.copy(
        pingleEntity = this.pingleEntity.copy(
            curParticipants = this.pingleEntity.curParticipants + if (this.pingleEntity.isParticipating) -PARTICIPANT_COUNT_STEP else PARTICIPANT_COUNT_STEP,
            isParticipating = !this.pingleEntity.isParticipating
        )
    )

    fun updateMainListPingleModelCompleted() = this.copy(
        pingleEntity = this.pingleEntity.copy(
            curParticipants = this.pingleEntity.maxParticipants
        )
    )

    companion object {
        private const val PARTICIPANT_COUNT_STEP = 1
    }
}
