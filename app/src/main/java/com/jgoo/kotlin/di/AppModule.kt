package com.jgoo.kotlin.di

import com.jgoo.kotlin.challenges.data.remote.ChallengesApi
import com.jgoo.kotlin.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideChallengeApi(): ChallengesApi {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val authInterceptor = Interceptor{ chain ->
            val request: Request = chain.request().newBuilder()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4MzA5NjU4NC01MDgzLTQ0NjktODFjNy1iYWIzMWFkZDJmNmEiLCJpYXQiOjE3Mjg4NzI1NjAsInRpbWVzdGFtcCI6MTcyODg3MjU2MC44NzY4MTYsImV4cCI6MTc5MTk0NDU2MH0.c-szmzZCRN0IJPFIDPqK_1DarhrkeH4hC5ov24myaOI")
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Add the OkHttpClient
            .build()
            .create(ChallengesApi::class.java)
    }

}