package com.example.habit_data.di

import android.content.Context
import com.example.core.room.HabitDao
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_data.api.HabitsApi
import com.example.habit_data.mappers.HabitMapper
import com.example.habit_data.mappers.HabitRemoteMapper
import com.example.habit_data.repository.HabitRepositoryImpl
import com.example.habit_domain.repository.HabitsRepository
import com.example.habit_domain.usecase.CreateHabitUseCase
import com.example.habit_domain.usecase.GetHabitByIdUseCase
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_domain.usecase.PerformHabitUseCase
import com.example.habit_domain.usecase.UpdateHabitUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
@Module
object HabitDataModule {

    @Provides
    fun provideContextNetworkConnectivityObserver(context: Context) : NetworkConnectivityObserver{
        return NetworkConnectivityObserver(context)
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

    @Provides
    fun provideCreateHabitUseCase(repository : HabitsRepository) : CreateHabitUseCase {
        return CreateHabitUseCase(repository)
    }

    @Provides
    fun provideGetHabitByIdUseCase(repository: HabitsRepository) : GetHabitByIdUseCase {
        return GetHabitByIdUseCase(repository)
    }

    @Provides
    fun provideGetHabitsUseCase(repository: HabitsRepository) : GetHabitsUseCase {
        return GetHabitsUseCase(repository)
    }

    @Provides
    fun provideUpdateHabitUseCase(repository: HabitsRepository) : UpdateHabitUseCase {
        return UpdateHabitUseCase(repository)
    }

    @Provides
    fun providePerformHabitUseCase(repository: HabitsRepository) : PerformHabitUseCase{
        return PerformHabitUseCase(repository)
    }
}