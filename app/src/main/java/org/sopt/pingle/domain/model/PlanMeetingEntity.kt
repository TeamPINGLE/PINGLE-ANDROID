package org.sopt.pingle.domain.model

data class PlanMeetingEntity(
    var category: String,
    var name: String,
    var startAt: String,
    var endAt: String,
    var x: Double,
    var y: Double,
    var address: String,
    var roadAddress: String,
    var location: String,
    var maxParticipants: Int,
    var chatLink: String
)
