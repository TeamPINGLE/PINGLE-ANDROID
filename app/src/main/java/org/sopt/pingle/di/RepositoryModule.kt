package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.data.repository.DummyDataRepositoryImpl
import org.sopt.pingle.data.repository.DummyRepositoryImpl
import org.sopt.pingle.domain.repository.DummyDataRepository
import org.sopt.pingle.domain.repository.DummyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsDummyDataRepository(dummyDataRepositoryImpl: DummyDataRepositoryImpl): DummyDataRepository

    @Binds
    @Singleton
    abstract fun bindsDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository
}
