package com.example.habittracker.core.utils

import android.util.Log
import kotlinx.coroutines.delay
import retrofit2.Response

suspend fun <T> makeRetryingApiCall(
    maxRetries: Int = 3,
    retryDelayMillis: Long = 1000,
    func: suspend () -> Response<T>
): Response<T> {
    var retryCount = 0
    while (retryCount < maxRetries) {
        try {
            val response = func()
            if (response.isSuccessful) {
                return response
            } else {
                retryCount++
                delay(retryDelayMillis)
            }
        } catch (e: Exception) {
            retryCount++
            Log.e("RetryRequest", "Thread ID: ${Thread.currentThread().id}", e)
            delay(retryDelayMillis)
        }
    }
    throw RuntimeException("Maximum number of retries reached. Unable to complete the request.")
}