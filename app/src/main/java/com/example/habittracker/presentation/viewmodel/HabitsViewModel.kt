package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.Habit

class HabitsViewModel(
    private val repository : HabitRepositoryImpl)
    : ViewModel()
{
    private val getHabitsUseCase = GetHabitsUseCase(repository)

    private var _habitList = MutableLiveData<List<Habit>?>()
    val habitList : LiveData<List<Habit>?> get() = _habitList

    init {
        getHabitList()
    }

    private fun getHabitList(){
        _habitList.value = getHabitsUseCase.invoke().value
    }

    class HabitsViewModelFactory (private val repository : HabitRepositoryImpl) :
        ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitsViewModel(
                repository
            ) as T
        }
    }
}