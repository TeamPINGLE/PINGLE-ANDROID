package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.domain.repository.DummyDataRepository
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
import org.sopt.pingle.domain.usecase.SetDummyDataUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesSetDummyDataUseCase(dummyDataRepository: DummyDataRepository): SetDummyDataUseCase =
        SetDummyDataUseCase(dummyDataRepository)

    @Provides
    @Singleton
    fun providesGetDummyUserListUseCase(dummyRepository: DummyRepository): GetDummyUserListUseCase =
        GetDummyUserListUseCase(dummyRepository)
}
