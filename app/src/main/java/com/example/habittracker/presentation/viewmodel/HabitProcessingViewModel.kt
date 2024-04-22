package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase

class HabitProcessingViewModel(
    private val createHabitUseCase : CreateHabitUseCase,
    private val updateHabitUseCase : UpdateHabitUseCase,
    private val getHabitItemUseCase : GetHabitItemUseCase)
    : ViewModel()
{

    private var _habitItem = MutableLiveData<Habit>()
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