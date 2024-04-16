package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

    private var habitList = getHabitsUseCase.invoke()
    private val filters: MutableLiveData<FilterParameters> =
        MutableLiveData(FilterParameters(null, null, null))
    val filteredHabit : MediatorLiveData<List<Habit>> = MediatorLiveData<List<Habit>>()

    init {
        filteredHabit.addSource(habitList) { habits ->
            val filtersValue = filters.value
            val filteredList = applyFilters(habits, filtersValue)
            filteredHabit.value = filteredList
        }

        filteredHabit.addSource(filters) { filterParams ->
            val habits = habitList.value
            if (habits != null) {
                val filteredList = applyFilters(habits, filterParams)
                filteredHabit.value = filteredList
            }
        }
    }

    fun getUsefulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.USEFUL }
    }

    fun getHarmfulHabit(habits : List<Habit>): List<Habit> {
        return habits.filter { it.type == HabitType.HARMFUL }
    }


    private fun List<Habit>.filterByName(valueFilter: String): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.filter{
            it.title.contains(valueFilter)
        }.toMutableList()
        return filteredAllHabits
    }

    fun cancelFilter(){
        filters.value = FilterParameters(null, null, null)
    }

    fun searchByName(name: String){
        val filter = filters.value!!
        if (name != CONST_LINE) {
            filter.habitTitle = name
        } else {
            filter.habitTitle = null
        }
        filters.value = filter
    }

    private fun applyFilters(habits: List<Habit>?, filter: FilterParameters?): List<Habit>{
        var filtrateObjects = habits ?: emptyList()
        if (filter?.habitTitle != null && habits != null){
            filtrateObjects = habits.filterByName(filter.habitTitle!!)
        }
        if (filter?.habitDescription != null && habits != null){

        }
        if (filter?.habitFrequency != null && habits != null){

        }
        return filtrateObjects
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