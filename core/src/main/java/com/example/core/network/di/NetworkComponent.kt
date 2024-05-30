package com.example.core.network.di

import android.content.Context
import com.example.core.room.HabitDao
import com.example.core.utils.NetworkConnectivityModule
import com.example.core.utils.NetworkConnectivityObserver
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

@Component(
    modules = [
        NetworkModule::class,
        CacheModule::class
    ]
)
interface NetworkComponent {

    fun getContext(): Context
    fun getRetrofit(): Retrofit
    fun getPopularDao(): HabitDao

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): NetworkComponent
    }
}