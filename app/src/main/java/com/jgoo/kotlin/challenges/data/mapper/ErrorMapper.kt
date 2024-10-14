package com.jgoo.kotlin.challenges.data.mapper

import com.jgoo.kotlin.challenges.domain.model.ApiError
import com.jgoo.kotlin.challenges.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toGeneralError(): NetworkError {
    val error = when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }
    return NetworkError(
        error = error,
        t = this
    )
}