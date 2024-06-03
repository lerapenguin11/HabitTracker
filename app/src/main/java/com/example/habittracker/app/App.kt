package com.example.habittracker.app

import android.app.Application
import com.example.core.network.di.DaggerNetworkComponent
import com.example.core.network.di.NetworkComponent
import com.example.habit_data.di.DaggerHabitDataComponent
import com.example.habit_data.di.HabitDataComponent
import com.example.habit_presentation.di.HabitsDepsStore
import com.example.habittracker.di.AppComponent
import com.example.habittracker.di.DaggerAppComponent

class App : Application() {
    private val  networkComponent: NetworkComponent by lazy {
        DaggerNetworkComponent
            .builder()
            .context(this)
            .build()
    }
    private val  habitDataComponent: HabitDataComponent by lazy {
        DaggerHabitDataComponent
            .builder()
            .networkComponent(networkComponent)
            .build()
    }

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .habitDataComponent(habitDataComponent)
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        HabitsDepsStore.deps = appComponent
    }
}