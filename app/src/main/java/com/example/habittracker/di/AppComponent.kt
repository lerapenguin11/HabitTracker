package com.example.habittracker.di

import com.example.habit_data.di.HabitDataModule
import com.example.habit_domain.di.HabitDomainModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Component(modules = [HabitDomainModule::class, HabitDataModule::class])
interface AppComponent {
}

@Module
class AppModule {

    /*@[Provides AppScope]
    //fun provideNewsService(@NewsApiQualifier apiKey: String) = NewsService(apiKey)*/
}

@Scope
annotation class AppScope