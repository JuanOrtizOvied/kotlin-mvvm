package com.jgoo.kotlin.challenges.presentation.challenges_screen

import com.jgoo.kotlin.challenges.domain.model.Challenge

data class ChallengesViewState(
    val isLoading: Boolean = false,
    val challenges: List<Challenge> = emptyList(),
    val error: String? = null
)