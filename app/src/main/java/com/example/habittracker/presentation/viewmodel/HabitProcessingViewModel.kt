package com.example.habittracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.core.network.ResultData
import com.example.habittracker.core.utils.ConnectivityObserver
import com.example.habittracker.core.utils.NetworkConnectivityObserver
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.TestGetHabitByIdUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HabitProcessingViewModel(
    private val updateHabitUseCase : UpdateHabitUseCase,
    private val updateHabitRemoteUseCase: UpdateHabitRemoteUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val nct : NetworkConnectivityObserver,
    private val testGetHabitByIdUseCase: TestGetHabitByIdUseCase
)
    : ViewModel()
{
    private var _habitItem = MutableLiveData<Habit>()
    val habitItem : LiveData<Habit> get() = _habitItem

    private val _networkStatus = MutableLiveData(ConnectivityObserver.Status.UNAVAILABLE)
    val networkStatus: LiveData<ConnectivityObserver.Status> = _networkStatus

    init {
        observeStatus()
    }

    private fun observeStatus() {
        viewModelScope.launch {
            nct.observerStatus().collect {
                _networkStatus.value = it
            }
        }
    }

    fun remoteCreateHabit(habit: Habit, status: ConnectivityObserver.Status) = viewModelScope.launch {
        createHabitUseCase.createHabit(newHabit = habit, status = status)
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

    fun loadHabitItemById(habitUID : String?, habitId : Long?){
        testGetHabitByIdUseCase.getHabitById(uid = habitUID, id = habitId)
            .onEach { _habitItem.value = it }
            .launchIn(viewModelScope)
    }

    fun updateHabit(habit : Habit) = viewModelScope.launch {
        updateHabitUseCase(habit = habit)
    }
}