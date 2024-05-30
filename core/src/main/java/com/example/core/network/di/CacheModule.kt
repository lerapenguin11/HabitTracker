package com.example.core.network.di

import android.content.Context
import com.example.core.room.HabitDao
import com.example.core.room.HabitDatabase
import dagger.Module
import dagger.Provides

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