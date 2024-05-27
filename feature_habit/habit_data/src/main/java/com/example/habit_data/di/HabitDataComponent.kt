package com.example.habit_data.di

import android.content.Context
import com.example.core.Feature
import com.example.core.network.di.CacheModule
import com.example.core.network.di.NetworkComponent
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_data.mappers.HabitMapper
import com.example.habit_data.mappers.HabitRemoteMapper
import com.example.habit_domain.di.HabitDomain
import com.example.habit_domain.repository.HabitsRepository
import com.example.habit_domain.usecase.CreateHabitUseCase
import com.example.habit_domain.usecase.GetHabitByIdUseCase
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_domain.usecase.UpdateHabitUseCase
import dagger.Component

@[Feature Component(modules = [HabitDataModule::class], dependencies = [NetworkComponent::class])]
interface HabitDataComponent : HabitDomain{
    fun provideHabitMapper(): HabitMapper
    fun provideHabitRemoteMapper(): HabitRemoteMapper
    fun provideHabitsRepository(): HabitsRepository

    fun provideGetHabitsUseCase() : GetHabitsUseCase

    fun provideCreateHabitUseCase() : CreateHabitUseCase

    fun provideGetHabitByIdUseCase() : GetHabitByIdUseCase

    fun provideUpdateHabitUseCase() : UpdateHabitUseCase

    fun provideContext() : Context

    fun getNetworkConnectivityObserver() : NetworkConnectivityObserver
}
