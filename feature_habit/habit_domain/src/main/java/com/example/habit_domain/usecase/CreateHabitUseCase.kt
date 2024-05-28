package com.example.habit_domain.usecase

import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.flow.flow

class CreateHabitUseCase(private val repository: HabitsRepository) {

    suspend fun createHabit(newHabit: Habit, status: ConnectivityObserver.Status) {
        when (status) {
            ConnectivityObserver.Status.AVAILABLE -> {
                val result = repository.createHabitFromServer(habit = newHabit)
                flow { emit(result) }
            }
            else -> {
                repository.createHabitFromDatabase(newHabit = newHabit)
            }
        }
    }
}