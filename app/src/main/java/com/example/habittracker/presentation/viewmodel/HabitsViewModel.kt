package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.domain.usecase.GetHabitsUseCase
import com.example.habittracker.presentation.model.FilterParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitsViewModel(
    private val getHabitsUseCase: GetHabitsUseCase
)
    : ViewModel(){
    //TODO: MediatorLiveData -> Flow.combine
    //val filteredHabit : MediatorLiveData<List<Habit>> = MediatorLiveData<List<Habit>>()
    private val _filterByDate = MutableLiveData(false)
    val filterByDate : LiveData<Boolean> = _filterByDate

    private val _usefulHabits = MutableLiveData<List<Habit>>(emptyList())
    val usefulHabit : LiveData<List<Habit>> = _usefulHabits
    private val _harmfulHabits = MutableStateFlow<List<Habit>>(emptyList())
    val harmfulHabit : StateFlow<List<Habit>> = _harmfulHabits

    private val filters: MutableLiveData<FilterParameters> =
        MutableLiveData(FilterParameters(null, null,
            null, null, null))

    val filteredUsefulHabits = MediatorLiveData<List<Habit>>()

    //TODO: посмотреть фильтры
    init {
        loadHabitList()
        filteredUsefulHabits.addSource(_usefulHabits) { habits ->
            val filtersValue = filters.value
            val filteredList = applyFilters(habits, filtersValue)
            filteredUsefulHabits.value = filteredList
        }
        filteredUsefulHabits.addSource(filters) { filterParams ->
            val habits = _usefulHabits.value
            if (habits != null) {
                val filteredList = applyFilters(habits, filterParams)
                filteredUsefulHabits.value = filteredList
            }
        }
        /*viewModelScope.launch {
            combine(_usefulHabits.asFlow(), filters){habits, filters ->
                withContext(Dispatchers.Default){
                    applyFilters(habits = habits, filter = filters)
                }
            }.collect{habits ->
                _filteredUsefulHabits.value = habits
            }
        }*/



        /*viewModelScope.launch {
            filters
                .flatMapLatest { filterParams ->
                    _usefulHabits.map { habits ->
                        applyFilters(habits, filterParams)
                    }
                }
                .collect { filteredHabits ->
                    _filteredUsefulHabits.value = filteredHabits
                }
        }*/
    }

    private fun loadHabitList() {
        viewModelScope.launch {
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
            }.collect { (useful, harmful) ->
                _usefulHabits.value = useful
                _harmfulHabits.value = harmful
            }
        }
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
        val filter = filters.value
        filter?.oldDate = true
        filter?.newDate = null
        _filterByDate.value = true
        filters.value = filter!!
    }

    fun searchByNewDate(){
        val filter = filters.value
        filter?.oldDate = null
        filter?.newDate = true
        _filterByDate.value = true
        filters.value = filter!!
    }

    fun searchByName(name: String){
        val filter = filters.value
        if (name != CONST_LINE) {
            filter?.habitTitle = name
        } else {
            filter?.habitTitle = null
        }
        filters.value = filter!!
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