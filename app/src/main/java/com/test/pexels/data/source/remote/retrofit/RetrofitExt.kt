package com.test.pexels.data.source.remote.retrofit

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

inline fun <reified T> Retrofit.create(): T {
    return create(T::class.java)
}

fun <T> Call<T>.bodyOrError(): T {
    return this.execute().bodyOrError()
}

fun <T> Response<T>.bodyOrError(): T {
    if (this.isSuccessful) return this.body()!!
    throw HttpException(this)
}

fun <T> Call<T>.isSuccessfulOrError(): Boolean {
    return this.execute().isSuccessfulOrError()
}

fun <T> Response<T>.isSuccessfulOrError(): Boolean {
    if (this.isSuccessful) return true
    throw HttpException(this)
}