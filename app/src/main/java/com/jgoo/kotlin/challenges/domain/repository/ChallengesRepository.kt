package com.jgoo.kotlin.challenges.domain.repository

import arrow.core.Either
import com.jgoo.kotlin.challenges.domain.model.Challenge
import com.jgoo.kotlin.challenges.domain.model.NetworkError

interface ChallengesRepository {
    suspend fun getChallenges(): Either<NetworkError, List<Challenge>>
}