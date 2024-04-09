package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase
import com.example.habittracker.presentation.model.Habit

class HabitProcessingViewModel(
    private val repository : HabitRepositoryImpl
) : ViewModel()
{
    private val createHabitUseCase = CreateHabitUseCase(repository)
    private val updateHabitUseCase = UpdateHabitUseCase(repository)

    fun updateHabit(habit : Habit){
        updateHabitUseCase.invoke(habit = habit)
    }

    fun createHabit(habit : Habit){
        createHabitUseCase.invoke(newHabit = habit)
    }

    class HabitProcessingViewModelFactory (private val repository : HabitRepositoryImpl) :
        ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitProcessingViewModel(
                repository
            ) as T
        }
    }
}