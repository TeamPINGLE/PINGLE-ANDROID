package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.repository.AuthRepositoryImpl
import org.sopt.pingle.data.repository.DummyRepositoryImpl
import org.sopt.pingle.data.repository.MapRepositoryImpl
import org.sopt.pingle.data.repository.PlanRepositoryImpl
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.repository.DummyRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.PlanRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindsMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsPlanRepository(planRepositoryImpl: PlanRepositoryImpl): PlanRepository
}
