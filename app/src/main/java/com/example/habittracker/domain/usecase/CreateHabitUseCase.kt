package com.example.habittracker.domain.usecase

import com.example.habittracker.core.utils.ConnectivityObserver
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.flow

class CreateHabitUseCase(private val repository: HabitsRepository) {

    suspend fun createHabit(newHabit: Habit, status: ConnectivityObserver.Status) {
        when (status) {
            ConnectivityObserver.Status.AVAILABLE -> {
                val result = repository.createHabitRemote(habit = newHabit)
                flow { emit(result) }
            }
            else -> {
                repository.createHabit(newHabit = newHabit)
            }
        }
    }
}