package com.example.habittracker.presentation.app

import android.app.Application
import com.example.habittracker.core.network.NetworkModule
import com.example.habittracker.data.api.HabitsApi
import com.example.habittracker.data.api.HabitsApiImpl
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.repository.HabitRepositoryImpl
import com.example.habittracker.data.room.HabitDatabase
import com.example.habittracker.domain.usecase.local.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.GetHabitsUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitsRemoteUseCase
import retrofit2.Retrofit

class BaseApplication : Application()
{
    private val dataBase by lazy { HabitDatabase.getInstance(this) }
    private val mapper by lazy { HabitMapper() }
    private val network by lazy { NetworkModule() }
    private val retrofit by lazy { network.getRetrofit() }
    private val habitsApi by lazy { HabitsApiImpl(retrofit) }
    private val repository by lazy { HabitRepositoryImpl(
        dao = dataBase.getHabitDao(), mapper = mapper, service = habitsApi) }

    private lateinit var mInstance: BaseApplication

    val getHabitsUseCase by lazy { GetHabitsUseCase(repository) }
    val getHabitsRemoteUseCase by lazy { GetHabitsRemoteUseCase(repository) }
    val createHabitUseCase by lazy { CreateHabitUseCase(repository) }
    val updateHabitUseCase by lazy { UpdateHabitUseCase(repository) }
    val getHabitItemUseCase by lazy { GetHabitItemUseCase(repository) }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}