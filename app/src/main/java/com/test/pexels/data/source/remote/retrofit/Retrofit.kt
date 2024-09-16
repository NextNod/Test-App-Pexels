package com.test.pexels.data.source.remote.retrofit

import com.google.gson.GsonBuilder
import com.test.pexels.BuildConfig
import com.test.pexels.data.source.remote.retrofit.interceptor.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object Retrofit {

    private const val MAX_LOG_LENGTH = Int.MAX_VALUE

    val base: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.pexels.com/v1/")
        .addConverterFactory(GsonConverterFactory.create(buildGson()))
        .client(buildHttpClient())
        .build()

    private fun buildHttpClient() = OkHttpClient.Builder().apply {
        addInterceptor(TokenInterceptor())
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) { Platform.get().log(message.take(MAX_LOG_LENGTH)) }
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        connectTimeout(30, SECONDS)
        writeTimeout(30, SECONDS)
        readTimeout(60, SECONDS)
    }.build()

    private fun buildGson() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()
}