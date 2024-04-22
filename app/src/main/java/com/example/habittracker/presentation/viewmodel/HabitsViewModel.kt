package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.FilterParameters

class HabitsViewModel(
    getHabitsUseCase: GetHabitsUseCase
)
    : ViewModel(){
    private var habitList : LiveData<List<Habit>>
    private val filters: MutableLiveData<FilterParameters> =
        MutableLiveData(FilterParameters(null, null, null))
    val filteredHabit : MediatorLiveData<List<Habit>> = MediatorLiveData<List<Habit>>()

    init {
        habitList = getHabitsUseCase.invoke()
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

    private fun List<Habit>.filterByDescription(valueFilter: String): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.filter{
            it.description.contains(valueFilter)
        }.toMutableList()
        return filteredAllHabits
    }

    private fun List<Habit>.filterByFrequency(valueFilter: String): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.filter{
            it.period.period == valueFilter
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

    fun searchByDescription(desc: String){
        val filter = filters.value!!
        if (desc != CONST_LINE) {
            filter.habitDescription = desc
        } else {
            filter.habitDescription = null
        }
        filters.value = filter
    }

    fun searchByFrequency(frequency : String){
        val filter = filters.value!!
        if (frequency != CONST_LINE) {
            filter.habitFrequency = frequency
        } else {
            filter.habitFrequency = null
        }
        filters.value = filter
    }


    private fun applyFilters(habits: List<Habit>?, filter: FilterParameters?): List<Habit>{
        var filtrateObjects = habits ?: emptyList()
        filter?.let {
            if (habits != null) {
                if (it.habitTitle != null) {
                    filtrateObjects = filtrateObjects.filterByName(it.habitTitle!!)
                }
                if (it.habitDescription != null) {
                    filtrateObjects = filtrateObjects.filterByDescription(it.habitDescription!!)
                }
                if (it.habitFrequency != null){
                    filtrateObjects = filtrateObjects.filterByFrequency(it.habitFrequency!!)
                }
            }
        }
        return filtrateObjects

    }

    companion object{
        private const val CONST_LINE = ""
    }
}