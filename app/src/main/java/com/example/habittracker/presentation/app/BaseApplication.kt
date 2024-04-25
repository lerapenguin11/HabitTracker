package com.example.habittracker.presentation.app

import android.app.Application
import com.example.habittracker.data.repository.HabitRepositoryImpl
import com.example.habittracker.data.room.HabitDatabase
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase

class BaseApplication : Application()
{
    private val dataBase by lazy { HabitDatabase.getInstance(this) }
    private val repository by lazy { HabitRepositoryImpl(dataBase.getHabitDao()) }

    private lateinit var mInstance: BaseApplication

    val getHabitsUseCase by lazy {GetHabitsUseCase(repository)}
    val createHabitUseCase by lazy { CreateHabitUseCase(repository) }
    val updateHabitUseCase by lazy { UpdateHabitUseCase(repository) }
    val getHabitItemUseCase by lazy { GetHabitItemUseCase(repository) }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}