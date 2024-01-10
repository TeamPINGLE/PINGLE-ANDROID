package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.repository.DummyRepositoryImpl
import org.sopt.pingle.data.repository.MapRepositoryImpl
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.repository.MapRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindsMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository
}
