package com.example.habittracker.di

import android.app.Application
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_data.di.HabitDataComponent
import com.example.habit_domain.repository.HabitsRepository
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_presentation.di.HabitsDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

@[AppScope Component(modules = [AppModule::class], dependencies = [HabitDataComponent::class])]
interface AppComponent : HabitsDeps{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(@HabitContextQualifier application: Application): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

        fun habitDataComponent(yourModule: HabitDataComponent): Builder
    }

    override val getHabitsUseCase: GetHabitsUseCase
    override val ntc: NetworkConnectivityObserver
}

@Module
class AppModule {

}

@Qualifier
annotation class HabitContextQualifier

@Scope
annotation class AppScope