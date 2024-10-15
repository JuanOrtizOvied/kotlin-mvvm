package com.jgoo.kotlin.challenges.domain.model
// import java.time.Instant

data class Challenge(
    val uuid: String,
    val code: String,
    val title: String,
    val description: String,
    // val end_date: Instant, // Use Instant for date-time
    val image_url: String,
    val reward: Reward
)

data class Reward(
    val uuid: String,
    val name: String,
    val image_url: String
)
