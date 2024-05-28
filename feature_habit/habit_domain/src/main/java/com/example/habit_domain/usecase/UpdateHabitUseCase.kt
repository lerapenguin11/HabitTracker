package com.example.habit_domain.usecase

import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.repository.HabitsRepository
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