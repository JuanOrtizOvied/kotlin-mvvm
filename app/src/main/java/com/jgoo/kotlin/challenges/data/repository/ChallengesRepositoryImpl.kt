package com.jgoo.kotlin.challenges.data.repository

import arrow.core.Either
import com.jgoo.kotlin.challenges.data.mapper.toGeneralError
import com.jgoo.kotlin.challenges.data.remote.ChallengesApi
import com.jgoo.kotlin.challenges.domain.model.Challenge
import com.jgoo.kotlin.challenges.domain.model.NetworkError
import com.jgoo.kotlin.challenges.domain.repository.ChallengesRepository
import javax.inject.Inject

class ChallengesRepositoryImpl @Inject constructor(
    private val challengesApi: ChallengesApi
): ChallengesRepository {
    override suspend fun getChallenges(): Either<NetworkError, List<Challenge>> {
        return Either.catch {
            val response = challengesApi.getChallenges() // Get ApiResponse
            if (response.detail == "ok"){
                response.result // Return result if successful
            } else {
                throw Exception("API error: ${response.detail}") // Throw exception if not "ok"
            }
        }.mapLeft { it.toGeneralError() }
    }
}