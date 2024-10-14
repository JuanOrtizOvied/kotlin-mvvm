package com.jgoo.kotlin.challenges.domain.model

data class ApiResponse<T>(
    val detail: String,
    val result: T
)