package com.example.habittracker.di

import android.app.Application
import com.example.core.AppScope
import com.example.habit_data.di.HabitDataComponent
import com.example.habit_presentation.di.HabitsDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier

@[AppScope Component(dependencies = [HabitDataComponent::class])]
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
}

@Qualifier
annotation class HabitContextQualifier