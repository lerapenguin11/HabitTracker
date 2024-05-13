package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.FilterParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitsViewModel(
    private val getHabitsUseCase: GetHabitsUseCase
)
    : ViewModel(){
    private val _filterByDate = MutableLiveData(false)
    val filterByDate : LiveData<Boolean> = _filterByDate

    private val _usefulHabits = MutableStateFlow<List<Habit>>(emptyList())
    private val _harmfulHabits = MutableStateFlow<List<Habit>>(emptyList())
    private val filters: MutableStateFlow<FilterParameters> =
        MutableStateFlow(FilterParameters(null, null,
            null, null, null))
    private val _filteredUsefulHabits = MutableLiveData<List<Habit>>(emptyList())
    val filteredUsefulHabits : LiveData<List<Habit>> = _filteredUsefulHabits
    private val _filteredHarmfulHabits = MutableLiveData<List<Habit>>(emptyList())
    val filteredHarmfulHabits : LiveData<List<Habit>> = _filteredHarmfulHabits

    init {
        loadHabitList()
        listenerFilteredUsefulHabits()
        listenerFilteredHarmfulHabits()
    }

    private fun listenerFilteredHarmfulHabits() {
        _harmfulHabits.combine(filters){habits, filters ->
            applyFilters(habits = habits, filter = filters)
        }
            .onEach {
                _filteredHarmfulHabits.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    private fun listenerFilteredUsefulHabits() {
        _usefulHabits.combine(filters){habits, filters ->
            applyFilters(habits = habits, filter = filters)
        }
            .onEach {
                _filteredUsefulHabits.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    private fun loadHabitList() {
        combine(getHabitsUseCase()){ allHabits ->
            Pair(
                withContext(Dispatchers.Default){
                    allHabits.flatMap {
                            habits -> habits.filter { habit -> habit.type == HabitType.USEFUL } }

                },
                withContext(Dispatchers.Default){
                    allHabits.flatMap {
                            habits -> habits.filter { habit -> habit.type == HabitType.HARMFUL } }
                }
            )
        }
            .distinctUntilChanged()
            .onEach { (useful, harmful) ->
                _usefulHabits.value = useful
                _harmfulHabits.value = harmful
            }
            .launchIn(viewModelScope)
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

    private fun List<Habit>.filterByNewDate(): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.sortedBy{
            it.dateCreation
        }.toMutableList()
        return filteredAllHabits
    }

    private fun List<Habit>.filterByOldDate(): MutableList<Habit> {
        val filteredAllHabits: MutableList<Habit> = this.sortedByDescending{
            it.dateCreation
        }.toMutableList()
        return filteredAllHabits
    }

    fun cancelFilter(){
        _filterByDate.value = false
        filters.value = FilterParameters(null, null,
            null, null, null)
    }

    fun searchByOldDate(){
        val currentFilter = filters.value
        val newFilter = FilterParameters(
            oldDate = true,
            newDate = null,
            habitDescription = currentFilter.habitDescription,
            habitFrequency = currentFilter.habitFrequency,
            habitTitle = currentFilter.habitTitle
        )
        filters.value = newFilter
    }

    fun searchByNewDate(){
        val currentFilter = filters.value
        val newFilter = FilterParameters(
            oldDate = null,
            newDate = true,
            habitDescription = currentFilter.habitDescription,
            habitFrequency = currentFilter.habitFrequency,
            habitTitle = currentFilter.habitTitle
        )
        filters.value = newFilter
    }

    fun searchByName(name: String){
        val currentFilter = filters.value
        val newFilter = FilterParameters(
            habitTitle = if (name != CONST_LINE) name else null,
            habitDescription = currentFilter.habitDescription,
            habitFrequency = currentFilter.habitFrequency,
            oldDate = currentFilter.oldDate,
            newDate = currentFilter.newDate
        )
        filters.value = newFilter
    }

    fun searchByDescription(desc: String){
        val currentFilter = filters.value
        val newFilter = FilterParameters(
            habitDescription = if (desc != CONST_LINE) desc else null,
            habitTitle = currentFilter.habitTitle,
            habitFrequency = currentFilter.habitFrequency,
            oldDate = currentFilter.oldDate,
            newDate = currentFilter.newDate
        )
        filters.value = newFilter
    }

    fun searchByFrequency(frequency : String){
        val currentFilter = filters.value
        val newFilter = FilterParameters(
            habitFrequency = if (frequency != CONST_LINE) frequency else null,
            habitDescription = currentFilter.habitDescription,
            habitTitle = currentFilter.habitTitle,
            oldDate = currentFilter.oldDate,
            newDate = currentFilter.newDate
        )
        filters.value = newFilter
    }


    private fun applyFilters(habits: List<Habit>?, filter: FilterParameters?):  List<Habit>{
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
                if (it.oldDate != null){
                    filtrateObjects = filtrateObjects.filterByOldDate()
                }
                if (it.newDate != null){
                    filtrateObjects = filtrateObjects.filterByNewDate()
                }
            }
        }
        return filtrateObjects
    }

    companion object{
        private const val CONST_LINE = ""
    }
}