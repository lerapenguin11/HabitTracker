package com.example.habittracker.app

import android.app.Application
import com.example.habittracker.di.AppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
    }
}