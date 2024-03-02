package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.repository.AuthRepositoryImpl
import org.sopt.pingle.data.repository.JoinGroupRepositoryImpl
import org.sopt.pingle.data.repository.MapRepositoryImpl
import org.sopt.pingle.data.repository.MyGroupListRepositoryImpl
import org.sopt.pingle.data.repository.ParticipantRepositoryImpl
import org.sopt.pingle.data.repository.PingleRepositoryImpl
import org.sopt.pingle.data.repository.PlanRepositoryImpl
import org.sopt.pingle.data.repository.RankingRepositoryImpl
import org.sopt.pingle.domain.repository.AuthRepository
import org.sopt.pingle.domain.repository.JoinGroupRepository
import org.sopt.pingle.domain.repository.MapRepository
import org.sopt.pingle.domain.repository.MyGroupListRepository
import org.sopt.pingle.domain.repository.ParticipantRepository
import org.sopt.pingle.domain.repository.PingleRepository
import org.sopt.pingle.domain.repository.PlanRepository
import org.sopt.pingle.domain.repository.RankingRepository

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

    @Binds
    @Singleton
    abstract fun bindsParticipantRepository(participantRepositoryImpl: ParticipantRepositoryImpl): ParticipantRepository

    @Binds
    @Singleton
    abstract fun bindsRankingRepository(rankingRepositoryImpl: RankingRepositoryImpl): RankingRepository

    @Binds
    @Singleton
    abstract fun bindsMyGroupListRepository(myGroupListRepositoryImpl: MyGroupListRepositoryImpl): MyGroupListRepository
}
