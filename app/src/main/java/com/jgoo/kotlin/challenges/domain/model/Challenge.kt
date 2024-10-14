package com.jgoo.kotlin.challenges.domain.model
// import java.time.Instant

data class Challenge(
    val uuid: String,
    val code: String,
    val title: String,
    val description: String,
    // val end_date: Instant, // Use Instant for date-time
    val image_url: String,
    val sponsor: Sponsor
)

data class Sponsor(
    val uuid: String,
    val logo_image_url: String
)
