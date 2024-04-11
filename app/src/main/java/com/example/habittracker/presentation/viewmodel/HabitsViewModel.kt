package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.FilterParameters
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType

class HabitsViewModel()
    : ViewModel(){
    private val repository = HabitRepositoryImpl
    private val getHabitsUseCase = GetHabitsUseCase(repository)

    var habitList = getHabitsUseCase.invoke()
    val filters: MutableLiveData<FilterParameters> =
        MutableLiveData(FilterParameters(null, null, null))



    fun getUsefulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.USEFUL }
    }

    fun getHarmfulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.HARMFUL }
    }


    fun List<Habit>.filterByName(valueFilter: String): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.filter{
            it.title.contains(valueFilter)
        }.toMutableList()
        return filteredAllHabits
    }

    fun cancelFilter(){
        filters.value = FilterParameters(null, null, null)
        habitList.value = getHabitsUseCase.invoke().value
    }

    fun searchByName(name: String){
        val filter = filters.value!!
        if (name != CONST_LINE) {
            filter.habitTitle = name
        } else {
            filter.habitTitle = null
        }
        filters.value = filter
        habitList.value = applyFilters(filters.value!!)
    }

    fun applyFilters(filters: FilterParameters,
                     filteredList: List<Habit> = habitList.value!!): List<Habit> {
        return if (filters.habitTitle != null) {
            filteredList.filterByName(filters.habitTitle!!)
        } else {
            filteredList
        }
    }

    companion object{
        private const val CONST_LINE = ""
    }

    class HabitsViewModelFactory () :
        ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HabitsViewModel() as T
        }
    }
}