package com.example.core.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observerStatus() : Flow<Status>

    enum class Status{
        AVAILABLE,
        UNAVAILABLE,
        LOSING,
        LOST
    }
}