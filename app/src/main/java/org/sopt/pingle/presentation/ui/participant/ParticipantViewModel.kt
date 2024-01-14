package org.sopt.pingle.presentation.ui.participant

import androidx.lifecycle.ViewModel
import org.sopt.pingle.domain.model.ParticipantEntity

class ParticipantViewModel : ViewModel() {

    val mockParticipantList = listOf<ParticipantEntity>(
        ParticipantEntity(
            participant = "내가 개최자다"
        ),
        ParticipantEntity(
            participant = "하지은"
        ),
        ParticipantEntity(
            participant = "내이름은되게긴데뭔지궁금하지안궁금하니궁금하니"
        ),
        ParticipantEntity(
            participant = "나는 배로로"
        ),
        ParticipantEntity(
            participant = "김민우"
        ),
        ParticipantEntity(
            participant = "박상준"
        ),
        ParticipantEntity(
            participant = "박재연"
        ),
        ParticipantEntity(
            participant = "이다은"
        ),
        ParticipantEntity(
            participant = "박소현"
        ),
        ParticipantEntity(
            participant = "장지원"
        ),
        ParticipantEntity(
            participant = "강민수"
        ),
        ParticipantEntity(
            participant = "정채은"
        ),
        ParticipantEntity(
            participant = "승욘핑"
        ),
        ParticipantEntity(
            participant = "최서인"
        ),
        ParticipantEntity(
            participant = "오슴도치"
        ),
        ParticipantEntity(
            participant = "방민지"
        )
    )
}
