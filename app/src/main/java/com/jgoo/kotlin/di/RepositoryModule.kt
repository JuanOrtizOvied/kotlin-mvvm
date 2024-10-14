package com.jgoo.kotlin.di

import com.jgoo.kotlin.challenges.data.repository.ChallengesRepositoryImpl
import com.jgoo.kotlin.challenges.domain.repository.ChallengesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideChallengesRepository(impl: ChallengesRepositoryImpl): ChallengesRepository

}