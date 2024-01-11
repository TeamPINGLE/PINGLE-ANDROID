package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
import org.sopt.pingle.domain.usecase.GetPingleListUseCase
import org.sopt.pingle.domain.usecase.PostPingleCancelUseCase
import org.sopt.pingle.domain.usecase.PostPingleParticipationUseCase
import org.sopt.pingle.domain.usecase.SetDummyDataUseCase
import javax.inject.Singleton

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
    fun providesPostPingleParticipationUseCase(pingleRepository: PingleRepository): PostPingleParticipationUseCase =
        PostPingleParticipationUseCase(pingleRepository = pingleRepository)

    @Provides
    @Singleton
    fun providesPostPingleCancelUseCase(pingleRepository: PingleRepository): PostPingleCancelUseCase =
        PostPingleCancelUseCase(pingleRepository = pingleRepository)
}
