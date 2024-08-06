package com.test.pexels.data.interceptor

import com.test.pexels.BuildConfig
import com.test.pexels.di.Dagger
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    private val prefs = Dagger.appComponent.providePreferencesRepository()

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val requestBuilder = original.newBuilder().url(
            original.url.newBuilder().build()
        )

        requestBuilder.addHeader("Authorization", BuildConfig.PIXELS_API_KEY)

        return chain.proceed(requestBuilder.build())
    }
}