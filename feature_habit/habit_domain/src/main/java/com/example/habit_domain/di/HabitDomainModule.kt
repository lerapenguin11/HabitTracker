package com.example.habit_domain.di

import com.example.habit_domain.repository.HabitsRepository
import com.example.habit_domain.usecase.CreateHabitUseCase
import com.example.habit_domain.usecase.GetHabitByIdUseCase
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_domain.usecase.UpdateHabitUseCase
import dagger.Module
import dagger.Provides

@Module
class HabitDomainModule {

    @Provides
    fun provideCreateHabitUseCase(repository : HabitsRepository) : CreateHabitUseCase {
        return CreateHabitUseCase(repository)
    }

    @Provides
    fun provideGetHabitByIdUseCase(repository: HabitsRepository) : GetHabitByIdUseCase{
        return GetHabitByIdUseCase(repository)
    }

    @Provides
    fun provideGetHabitsUseCase(repository: HabitsRepository) : GetHabitsUseCase{
        return GetHabitsUseCase(repository)
    }

    @Provides
    fun provideUpdateHabitUseCase(repository: HabitsRepository) : UpdateHabitUseCase{
        return UpdateHabitUseCase(repository)
    }
}