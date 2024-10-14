package com.jgoo.kotlin.challenges.data.remote

import com.jgoo.kotlin.challenges.domain.model.ApiResponse
import com.jgoo.kotlin.challenges.domain.model.Challenge
import retrofit2.http.GET

interface ChallengesApi {
    @GET("v2/challenge")
    suspend fun getChallenges(): ApiResponse<List<Challenge>>
}