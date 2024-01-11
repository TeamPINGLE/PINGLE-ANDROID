package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.repository.JoinGroupCodeRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
import org.sopt.pingle.domain.usecase.GetJoinGroupInfoUseCase
import org.sopt.pingle.domain.usecase.GetPinListWithoutFilteringUseCase
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
    fun providesGetJoinGroupInfoUseCase(joinGroupCodeRepository: JoinGroupCodeRepository): GetJoinGroupInfoUseCase =
        GetJoinGroupInfoUseCase(joinGroupCodeRepository = joinGroupCodeRepository)
}
