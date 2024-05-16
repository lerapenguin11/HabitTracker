package com.example.habittracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.core.network.ResultData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitUID
import com.example.habittracker.domain.usecase.local.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.CreateHabitRemoteUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitByUIDUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HabitProcessingViewModel(
    private val createHabitUseCase : CreateHabitUseCase,
    private val updateHabitUseCase : UpdateHabitUseCase,
    private val getHabitItemUseCase : GetHabitItemUseCase,
    private val createHabitRemoteUseCase: CreateHabitRemoteUseCase,
    private val updateHabitRemoteUseCase: UpdateHabitRemoteUseCase,
    private val getHabitByUIDUseCase: GetHabitByUIDUseCase
)
    : ViewModel()
{
    private var _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    private val _newHabitUID = MutableLiveData<HabitUID?>()
    val newHabitUID : MutableLiveData<HabitUID?> get() = _newHabitUID

    val test = MutableLiveData<Boolean>(true)

    fun remoteCreateHabit(habit: Habit) = viewModelScope.launch {
        when (val response = createHabitRemoteUseCase(newHabit = habit)) {
            is ResultData.Success -> {
                Log.d("ADD HABIT: ", response.data.toString())
                _newHabitUID.value = response.data
            }

            is ResultData.Error -> {
                createHabit(habit)
                Log.e("ADD HABIT ERROR: ", response.exception.toString())
            }
        }
        test.value = false
    }

    fun remoteUpdateHabit(habit: Habit) = viewModelScope.launch {
        when(val response = updateHabitRemoteUseCase(habit = habit)){
            is ResultData.Success ->{
                Log.d("ADD HABIT: ", response.data.toString())
            }
            is ResultData.Error ->{
                updateHabit(habit)
                Log.e("UPDATE HABIT ERROR: ", response.exception.toString())
            }
        }
    }

    fun loadHabitItemByUID(habitUID : String){
        getHabitByUIDUseCase(habitUID = habitUID)
            .onEach { _habitItem.value  = it }
            .launchIn(viewModelScope)
    }

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