package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType

class HabitsViewModel()
    : ViewModel(){
    private val repository = HabitRepositoryImpl
    private val getHabitsUseCase = GetHabitsUseCase(repository)

    val habitList = getHabitsUseCase.invoke()
    private val habitListEmpty = listOf<Habit>()

    fun getUsefulHabit(habits : List<Habit>): List<Habit> {
        return habitList.value?.filter { it.type == HabitType.USEFUL } ?: habitListEmpty
    }

    fun getHarmfulHabit(habits : List<Habit>): List<Habit> {
        return habits?.filter { it.type == HabitType.HARMFUL } ?: habitListEmpty
    }

    class HabitsViewModelFactory () :
        ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitsViewModel() as T
        }
    }
}