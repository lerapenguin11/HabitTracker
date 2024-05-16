package com.example.habittracker.presentation.app

import android.app.Application
import com.example.habittracker.core.network.NetworkModule
import com.example.habittracker.data.api.HabitsApiImpl
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.mappers.HabitRemoteMapper
import com.example.habittracker.data.repository.HabitRepositoryImpl
import com.example.habittracker.data.room.HabitDatabase
import com.example.habittracker.domain.usecase.local.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.GetHabitsUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.CreateHabitRemoteUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitByUIDUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitsRemoteUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase
import com.example.habittracker.presentation.viewmodel.HabitProcessingViewModelFactory
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.example.habittracker.presentation.viewmodel.HabitsViewModelFactory

class BaseApplication : Application()
{
    private val dataBase by lazy { HabitDatabase.getInstance(this) }
    private val localMapper by lazy { HabitMapper() }
    private val remoteMapper by lazy { HabitRemoteMapper() }
    private val network by lazy { NetworkModule() }
    private val retrofit by lazy { network.getRetrofit() }
    private val habitsApi by lazy { HabitsApiImpl(retrofit) }
    private val repository by lazy { HabitRepositoryImpl(
        dao = dataBase.getHabitDao(), localMapper = localMapper,
        service = habitsApi, remoteMapper = remoteMapper) }

    private lateinit var mInstance: BaseApplication

    private val getHabitsUseCase by lazy { GetHabitsUseCase(repository) }
    private val getHabitsRemoteUseCase by lazy { GetHabitsRemoteUseCase(repository) }
    private val createHabitUseCase by lazy { CreateHabitUseCase(repository) }
    private val updateHabitUseCase by lazy { UpdateHabitUseCase(repository) }
    private val getHabitItemUseCase by lazy { GetHabitItemUseCase(repository) }
    private val createHabitRemoteUseCase by lazy { CreateHabitRemoteUseCase(repository) }
    private val updateHabitRemoteUseCase by lazy { UpdateHabitRemoteUseCase(repository) }
    private val getHabitByUIDUseCase by lazy { GetHabitByUIDUseCase(repository) }


    val habitProcessingViewModelFactory by lazy { HabitProcessingViewModelFactory(
        createHabitUseCase = createHabitUseCase,
        updateHabitUseCase = updateHabitUseCase,
        getHabitItemUseCase = getHabitItemUseCase,
        createHabitRemoteUseCase = createHabitRemoteUseCase,
        updateHabitRemoteUseCase = updateHabitRemoteUseCase,
        getHabitByUIDUseCase = getHabitByUIDUseCase
    ) }

    val habitsViewModelFactory by lazy {
        HabitsViewModelFactory(
            getHabitsUseCase,
            getHabitsRemoteUseCase)
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}