package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.repository.PlanMeetingRepository
import org.sopt.pingle.domain.repository.PlanRepository
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.domain.usecase.GetPlanLocationListUseCase
import org.sopt.pingle.domain.usecase.PostPingleCancelUseCase
import org.sopt.pingle.domain.usecase.PostPingleJoinUseCase
import org.sopt.pingle.domain.usecase.PostPlanMeetingUseCase
import org.sopt.pingle.domain.usecase.SetDummyDataUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesSetDummyDataUseCase(dummyRepository: DummyRepository): SetDummyDataUseCase =
        SetDummyDataUseCase(dummyRepository)

    @Provides
    @Singleton
    fun providesGetDummyUserListUseCase(dummyRepository: DummyRepository): GetDummyUserListUseCase =
        GetDummyUserListUseCase(dummyRepository)

    @Provides
    @Singleton
    fun providesGetPinListWithoutFilteringUseCase(mapRepository: MapRepository): GetPinListWithoutFilteringUseCase =
        GetPinListWithoutFilteringUseCase(mapRepository = mapRepository)

    @Provides
    @Singleton
    fun providesGetPingleListUseCase(mapRepository: MapRepository): GetPingleListUseCase =
        GetPingleListUseCase(mapRepository = mapRepository)

    @Provides
    @Singleton
    fun providesPostPingleJoinUseCase(pingleRepository: PingleRepository): PostPingleJoinUseCase =
        PostPingleJoinUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesPostPingleCancelUseCase(pingleRepository: PingleRepository): PostPingleCancelUseCase =
        PostPingleCancelUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesGetPlanLocationListUseCase(planRepository: PlanRepository): GetPlanLocationListUseCase =
        GetPlanLocationListUseCase(planRepository = planRepository)

    @Provides
    @Singleton
    fun providesPostPlanMeetingUseCase(planMeetingRepository: PlanMeetingRepository): PostPlanMeetingUseCase =
        PostPlanMeetingUseCase(planMeetingRepository = planMeetingRepository)
}
