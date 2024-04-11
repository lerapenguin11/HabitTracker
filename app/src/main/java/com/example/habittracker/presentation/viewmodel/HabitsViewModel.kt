package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.FilterParameters
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitRepetitionPeriod
import com.example.habittracker.presentation.model.HabitType

class HabitsViewModel()
    : ViewModel(){
    private val repository = HabitRepositoryImpl
    private val getHabitsUseCase = GetHabitsUseCase(repository)

    var habitList = getHabitsUseCase.invoke()
    private val habitListEmpty = listOf<Habit>()
    val filters: MutableLiveData<FilterParameters> =
        MutableLiveData(FilterParameters(null, null, null))
    private val _filterHabitList = MutableLiveData<List<Habit>>()
    val filterHabitList : LiveData<List<Habit>> get() = _filterHabitList

    fun getUsefulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.USEFUL } ?: habitListEmpty
    }

    fun getHarmfulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.HARMFUL } ?: habitListEmpty
    }


    fun List<Habit>.filterByName(valueFilter: String): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.filter{
            it.title.contains(valueFilter)
        }.toMutableList()
        return filteredAllHabits
    }

    fun List<Habit>.filterByTitle(valueFilter: String): List<Habit> {
        val filteredAllHabits: List<Habit> = this.filter{
            it.title.contains(valueFilter)
        }.toMutableList()
        return filteredAllHabits
    }

    fun resetFilter(){
        filters.value = FilterParameters(null, null, null)
        habitList.value = applyFilters(filters.value!!, habitList.value!!)
    }

    fun searchByName(name: String){
        val filter = filters.value!!
        val const = ""
        if (name != const) {
            filter.habitTitle = name
        } else {
            filter.habitTitle = null
        }
        filters.value = filter
        habitList.value = applyFilters(filters.value!!, habitList.value!!)
    }

    fun applyFilters(filters: FilterParameters, filteredList: List<Habit> = habitList.value!!) : List<Habit>{
        if (filters.habitTitle != null){
            _filterHabitList.value = filteredList.filterByName(filters.habitTitle!!)
        } else{
            _filterHabitList.value = habitList.value
        }
        return _filterHabitList.value!!
    }

    class HabitsViewModelFactory () :
        ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitsViewModel() as T
        }
    }
}