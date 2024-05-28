package com.example.core.network.di

import android.content.Context
import com.example.core.room.HabitDao
import com.example.core.room.HabitDatabase
import com.example.core.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object CacheModule {

    @Provides
    fun provideHabitsDatabase(context : Context): HabitDatabase {
        return HabitDatabase.getInstance(context)
    }

    @Provides
    fun provideHabitsDao(appDatabase: HabitDatabase): HabitDao {
        return appDatabase.getHabitDao()
    }
}