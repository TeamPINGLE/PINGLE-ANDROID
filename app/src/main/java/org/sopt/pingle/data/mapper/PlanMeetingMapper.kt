package org.sopt.pingle.data.mapper

import org.sopt.pingle.data.model.remote.request.RequestPlanMeetingDto
import org.sopt.pingle.domain.model.PlanMeetingEntity

fun PlanMeetingEntity.toRequestPlanMeetingDto() = RequestPlanMeetingDto(
    category = this.category,
    meetingName = this.name,
    startTime = this.startAt,
    endTime = this.endAt,
    coordinateX = this.x,
    coordinateY = this.y,
    address = this.address,
    roadAddress = this.roadAddress,
    locationName = this.location,
    maxParticipants = this.maxParticipants,
    chatLink = this.chatLink
)
