package com.example.habittracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.habittracker.data.entity.HabitDatabase
import com.example.habittracker.data.repository.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase
import com.example.habittracker.domain.model.Habit

class HabitProcessingViewModel(
    applicationContext: Application)
    : AndroidViewModel(applicationContext)
{
    private val repository : HabitRepositoryImpl //TODO: удалить
    //TODO: вынести в конструктор
    private val createHabitUseCase : CreateHabitUseCase
    private val updateHabitUseCase : UpdateHabitUseCase
    private val getHabitItemUseCase : GetHabitItemUseCase

    private var _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    init {
        val db = Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java, "habit_db"
        ).allowMainThreadQueries().build().getHabitDao()
        repository = HabitRepositoryImpl(db)
        createHabitUseCase = CreateHabitUseCase(repository)
        updateHabitUseCase = UpdateHabitUseCase(repository)
        getHabitItemUseCase = GetHabitItemUseCase(repository)
    }

    fun getHabitItem(habitId : Int){
        _habitItem.value = getHabitItemUseCase.invoke(habitId = habitId).value
    }

    fun updateHabit(habit : Habit){
        updateHabitUseCase.invoke(habit = habit)
    }

    fun createHabit(habit : Habit){
        createHabitUseCase.invoke(newHabit = habit)
    }
}