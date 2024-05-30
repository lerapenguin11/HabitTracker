package com.example.core.utils

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class NetworkConnectivityModule(private val context: Context) {

    @Provides
    fun provideContextNetworkConnectivityObserver() : NetworkConnectivityObserver{
        return NetworkConnectivityObserver(context)
    }
}