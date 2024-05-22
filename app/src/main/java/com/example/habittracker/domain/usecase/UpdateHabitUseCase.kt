package com.example.habittracker.domain.usecase

import com.example.habittracker.core.utils.ConnectivityObserver
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.flow

class UpdateHabitUseCase(private val repository: HabitsRepository) {
    suspend fun updateHabit(habit : Habit, status: ConnectivityObserver.Status){
        when (status) {
            ConnectivityObserver.Status.AVAILABLE -> {
                val result = repository.updateHabitFromServer(habit = habit)
                flow { emit(result) }
            }
            else -> {
                repository.updateHabitFromDatabase(habit = habit)
            }
        }
    }
}