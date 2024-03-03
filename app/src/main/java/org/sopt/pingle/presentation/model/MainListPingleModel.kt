package org.sopt.pingle.presentation.model

import androidx.databinding.ObservableBoolean
import org.sopt.pingle.domain.model.PingleEntity

data class MainListPingleModel(
    val pingleEntity: PingleEntity,
    var isExpanded: ObservableBoolean = ObservableBoolean(false)
) {
    fun updateMainListPingleModel() = this.copy(
        pingleEntity = this.pingleEntity.copy(
            curParticipants = if (this.pingleEntity.isParticipating) this.pingleEntity.curParticipants + 1 else this.pingleEntity.curParticipants - 1,
            isParticipating = !this.pingleEntity.isParticipating
        )
    )
}
