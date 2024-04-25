package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitProcessingViewModel(
    private val createHabitUseCase : CreateHabitUseCase,
    private val updateHabitUseCase : UpdateHabitUseCase,
    private val getHabitItemUseCase : GetHabitItemUseCase)
    : ViewModel()
{

    private var _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    fun loadHabitItem(habitId : Int) = viewModelScope.launch {
        _habitItem = getHabitItemUseCase.invoke(habitId = habitId)
            .asLiveData(Dispatchers.IO) as MutableLiveData<Habit>
    }

    fun updateHabit(habit : Habit) = viewModelScope.launch {
        updateHabitUseCase.invoke(habit = habit)
    }

    fun createHabit(habit : Habit) = viewModelScope.launch {
        createHabitUseCase.invoke(newHabit = habit)
    }
}