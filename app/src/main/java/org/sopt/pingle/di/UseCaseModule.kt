package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.repository.JoinGroupRepository
import org.sopt.pingle.domain.repository.MainListRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.MyGroupListRepository
import org.sopt.pingle.domain.repository.NewGroupRepository
import org.sopt.pingle.domain.repository.ParticipantRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.repository.PlanRepository
import org.sopt.pingle.domain.repository.RankingRepository
import org.sopt.pingle.domain.usecase.DeletePingleCancelUseCase
import org.sopt.pingle.domain.usecase.DeletePingleDeleteUseCase
import org.sopt.pingle.domain.usecase.GetJoinGroupInfoUseCase
import org.sopt.pingle.domain.usecase.GetJoinGroupSearchUseCase
import org.sopt.pingle.domain.usecase.GetMainListPingleListUseCase
import org.sopt.pingle.domain.usecase.GetMapPingleListUseCase
import org.sopt.pingle.domain.usecase.GetMyGroupListUseCase
import org.sopt.pingle.domain.usecase.GetMyPingleListUseCase
import org.sopt.pingle.domain.usecase.GetNewGroupCheckNameUseCase
import org.sopt.pingle.domain.usecase.GetNewGroupKeywordsUserCase
import org.sopt.pingle.domain.usecase.GetParticipantListUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPlanLocationListUseCase
import org.sopt.pingle.domain.usecase.GetRankingUseCase
import org.sopt.pingle.domain.usecase.GetUserInfoUseCase
import org.sopt.pingle.domain.usecase.PostJoinGroupCodeUseCase
import org.sopt.pingle.domain.usecase.PostNewGroupCreateUseCase
import org.sopt.pingle.domain.usecase.PostPingleJoinUseCase
import org.sopt.pingle.domain.usecase.PostPlanMeetingUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesDeletePingleCancelUseCase(pingleRepository: PingleRepository): DeletePingleCancelUseCase =
        DeletePingleCancelUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesDeletePingleDeleteUseCase(pingleRepository: PingleRepository): DeletePingleDeleteUseCase =
        DeletePingleDeleteUseCase(pingleRepository = pingleRepository)

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
    fun providesGetMainListPingleListUseCase(mainListRepository: MainListRepository): GetMainListPingleListUseCase =
        GetMainListPingleListUseCase(mainListRepository = mainListRepository)

    @Provides
    @Singleton
    fun providesGetMapPingleListUseCase(mapRepository: MapRepository): GetMapPingleListUseCase =
        GetMapPingleListUseCase(mapRepository = mapRepository)

    @Provides
    @Singleton
    fun providesGetMyPingleListUseCase(pingleRepository: PingleRepository): GetMyPingleListUseCase =
        GetMyPingleListUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesGetParticipantListUseCase(participantRepository: ParticipantRepository): GetParticipantListUseCase =
        GetParticipantListUseCase(participantRepository = participantRepository)

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
    fun providesGetRankingUseCase(rankingRepository: RankingRepository): GetRankingUseCase =
        GetRankingUseCase(rankingRepository = rankingRepository)

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
    fun providesPostPingleJoinUseCase(pingleRepository: PingleRepository): PostPingleJoinUseCase =
        PostPingleJoinUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesPostPlanMeetingUseCase(planRepository: PlanRepository): PostPlanMeetingUseCase =
        PostPlanMeetingUseCase(planRepository = planRepository)

    @Provides
    @Singleton
    fun providesGetMyGroupListUseCase(myGroupListRepository: MyGroupListRepository): GetMyGroupListUseCase =
        GetMyGroupListUseCase(myGroupListRepository = myGroupListRepository)

    @Provides
    @Singleton
    fun providesGetNewGroupKeywordsUseCase(newGroupRepository: NewGroupRepository): GetNewGroupKeywordsUserCase =
        GetNewGroupKeywordsUserCase(newGroupRepository = newGroupRepository)

    @Provides
    @Singleton
    fun providesGetNewGroupCheckNameUseCase(newGroupRepository: NewGroupRepository): GetNewGroupCheckNameUseCase =
        GetNewGroupCheckNameUseCase(newGroupRepository = newGroupRepository)

    @Provides
    @Singleton
    fun providesPostNewGroupCreateUseCase(newGroupRepository: NewGroupRepository): PostNewGroupCreateUseCase =
        PostNewGroupCreateUseCase(newGroupRepository = newGroupRepository)
}
