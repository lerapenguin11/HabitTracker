package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.usecase.local.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HabitProcessingViewModel(
    private val createHabitUseCase : CreateHabitUseCase,
    private val updateHabitUseCase : UpdateHabitUseCase,
    private val getHabitItemUseCase : GetHabitItemUseCase
)
    : ViewModel()
{
    private var _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    fun loadHabitItem(habitId : String) {
        getHabitItemUseCase(habitId = habitId)
            .onEach { _habitItem.value = it}
            .launchIn(viewModelScope)
    }

    fun updateHabit(habit : Habit) = viewModelScope.launch {
        updateHabitUseCase(habit = habit)
    }

    fun createHabit(habit : Habit) = viewModelScope.launch {
        createHabitUseCase(newHabit = habit)
    }
}