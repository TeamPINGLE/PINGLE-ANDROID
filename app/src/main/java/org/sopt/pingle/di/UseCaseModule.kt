package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.usecase.GetDummyUserListUseCase
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
}
