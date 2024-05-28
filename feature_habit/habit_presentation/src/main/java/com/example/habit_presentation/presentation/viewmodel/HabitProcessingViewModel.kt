package com.example.habit_presentation.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core.utils.ConnectivityObserver
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.usecase.CreateHabitUseCase
import com.example.habit_domain.usecase.GetHabitByIdUseCase
import com.example.habit_domain.usecase.UpdateHabitUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitProcessingViewModel(
    private val createHabitUseCase: CreateHabitUseCase,
    private val nct : NetworkConnectivityObserver,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
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

    fun remoteUpdateHabit(habit: Habit, status: ConnectivityObserver.Status) = viewModelScope.launch {
        updateHabitUseCase.updateHabit(habit = habit, status = status)
        /*when(val response = updateHabitRemoteUseCase(habit = habit)){
            is ResultData.Success ->{
                Log.d("ADD HABIT: ", response.data.toString())
            }
            is ResultData.Error ->{
                updateHabit(habit)
                Log.e("UPDATE HABIT ERROR: ", response.exception.toString())
            }
        }*/
    }

    fun loadHabitItemById(habitUID : String?, habitId : Long?){
        getHabitByIdUseCase.getHabitById(uid = habitUID, id = habitId)
            .onEach { _habitItem.value = it }
            .launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val createHabitUseCase: CreateHabitUseCase,
        private val nct : NetworkConnectivityObserver,
        private val getHabitByIdUseCase: GetHabitByIdUseCase,
        private val updateHabitUseCase: UpdateHabitUseCase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == HabitProcessingViewModel::class.java)
            return HabitProcessingViewModel(
                createHabitUseCase = createHabitUseCase,
                nct = nct,
                getHabitByIdUseCase = getHabitByIdUseCase,
                updateHabitUseCase = updateHabitUseCase) as T
        }
    }
}