package com.example.habit_data.di

import android.content.Context
import com.example.core.room.HabitDao
import com.example.core.room.HabitDatabase
import com.example.habit_data.api.HabitsApi
import com.example.habit_data.mappers.HabitMapper
import com.example.habit_data.mappers.HabitRemoteMapper
import com.example.habit_data.repository.HabitRepositoryImpl
import com.example.habit_domain.repository.HabitsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class HabitDataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideHabitsDatabase(): HabitDatabase {
        return HabitDatabase.getInstance(context)
    }

    @Provides
    fun provideHabitsDao(appDatabase: HabitDatabase): HabitDao {
        return appDatabase.getHabitDao()
    }

    @Provides
    fun provideHabitApiService(retrofit: Retrofit): HabitsApi {
        return retrofit.create(HabitsApi::class.java)
    }

    @Provides
    fun provideHabitMapper() : HabitMapper{
        return HabitMapper()
    }

    @Provides
    fun provideHabitRemoteMapper() : HabitRemoteMapper{
        return HabitRemoteMapper()
    }

    @Provides
    fun provideHabitsRepositoryImpl(
        dao : HabitDao, localMapper : HabitMapper,
        remoteMapper: HabitRemoteMapper, service : HabitsApi):
            HabitsRepository {
        return HabitRepositoryImpl(dao = dao, localMapper = localMapper,
            remoteMapper = remoteMapper, service = service)
    }
}