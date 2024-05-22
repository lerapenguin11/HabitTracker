package com.example.habittracker.core.network.interceptor

import com.example.habittracker.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeadersHabitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Accept", "application/json")
            .header("Authorization", BuildConfig.API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}