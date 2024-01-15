package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.repository.JoinGroupRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.repository.PlanRepository
import org.sopt.pingle.domain.usecase.GetJoinGroupInfoUseCase
import org.sopt.pingle.domain.usecase.GetJoinGroupSearchUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.domain.usecase.GetPingleParticipationList
import org.sopt.pingle.domain.usecase.GetPlanLocationListUseCase
import org.sopt.pingle.domain.usecase.GetUserInfoUseCase
import org.sopt.pingle.domain.usecase.PostJoinGroupCodeUseCase
import org.sopt.pingle.domain.usecase.PostPingleCancelUseCase
import org.sopt.pingle.domain.usecase.PostPingleJoinUseCase
import org.sopt.pingle.domain.usecase.PostPlanMeetingUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesGetJoinGroupInfoUseCase(joinGroupRepository: JoinGroupRepository): GetJoinGroupInfoUseCase =
        GetJoinGroupInfoUseCase(joinGroupRepository = joinGroupRepository)

    @Provides
    @Singleton
    fun providesGetJoinGroupSearchUseCase(joinGroupRepository: JoinGroupRepository): GetJoinGroupSearchUseCase =
        GetJoinGroupSearchUseCase(joinGroupRepository = joinGroupRepository)

    @Provides
    @Singleton
    fun providesGetPingleListUseCase(mapRepository: MapRepository): GetPingleListUseCase =
        GetPingleListUseCase(mapRepository = mapRepository)

    @Provides
    @Singleton
    fun providesGetPinListWithoutFilteringUseCase(mapRepository: MapRepository): GetPinListWithoutFilteringUseCase =
        GetPinListWithoutFilteringUseCase(mapRepository = mapRepository)

    @Provides
    @Singleton
    fun providesGetPlanLocationListUseCase(planRepository: PlanRepository): GetPlanLocationListUseCase =
        GetPlanLocationListUseCase(planRepository = planRepository)

    @Provides
    @Singleton
    fun providesGetUserInfoUseCase(authRepository: AuthRepository): GetUserInfoUseCase =
        GetUserInfoUseCase(authRepository = authRepository)

    @Provides
    @Singleton
    fun providesPostJoinGroupCodeUseCase(joinGroupRepository: JoinGroupRepository): PostJoinGroupCodeUseCase =
        PostJoinGroupCodeUseCase(joinGroupRepository = joinGroupRepository)

    @Provides
    @Singleton
    fun providesPostPingleCancelUseCase(pingleRepository: PingleRepository): PostPingleCancelUseCase =
        PostPingleCancelUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesPostPingleJoinUseCase(pingleRepository: PingleRepository): PostPingleJoinUseCase =
        PostPingleJoinUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesPostPlanMeetingUseCase(planRepository: PlanRepository): PostPlanMeetingUseCase =
        PostPlanMeetingUseCase(planRepository = planRepository)

    @Provides
    @Singleton
    fun providesGetPingleParticipationList(pingleRepository: PingleRepository): GetPingleParticipationList =
        GetPingleParticipationList(pingleRepository = pingleRepository)
}
