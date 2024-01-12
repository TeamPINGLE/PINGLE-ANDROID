package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.data.repository.AuthRepositoryImpl
import org.sopt.pingle.data.repository.JoinGroupRepositoryImpl
import org.sopt.pingle.data.repository.MapRepositoryImpl
import org.sopt.pingle.data.repository.PingleRepositoryImpl
import org.sopt.pingle.data.repository.PlanRepositoryImpl
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.repository.JoinGroupRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.repository.PlanRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsJoinGroupRepository(joinGroupRepositoryImpl: JoinGroupRepositoryImpl): JoinGroupRepository

    @Binds
    @Singleton
    abstract fun bindsMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    abstract fun bindsPingleRepository(pingleRepositoryImpl: PingleRepositoryImpl): PingleRepository

    @Binds
    @Singleton
    abstract fun bindsPlanRepository(planRepositoryImpl: PlanRepositoryImpl): PlanRepository
}
