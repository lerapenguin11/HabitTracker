package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.repository.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase
import com.example.habittracker.domain.model.Habit

class HabitProcessingViewModel() : ViewModel()
{
    private val repository = HabitRepositoryImpl //TODO: удалить
    //TODO: вынести в конструктор
    private val createHabitUseCase = CreateHabitUseCase(repository)
    private val updateHabitUseCase = UpdateHabitUseCase(repository)
    private val getHabitItemUseCase = GetHabitItemUseCase(repository)

    private val _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    fun getHabitItem(habitId : Int){
        _habitItem.value = getHabitItemUseCase.invoke(habitId = habitId)
    }

    fun updateHabit(habit : Habit){
        updateHabitUseCase.invoke(habit = habit)
    }

    fun createHabit(habit : Habit){
        createHabitUseCase.invoke(newHabit = habit)
    }
}