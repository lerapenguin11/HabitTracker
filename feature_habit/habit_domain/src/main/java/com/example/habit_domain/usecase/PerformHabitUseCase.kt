package com.example.habit_domain.usecase

import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.repository.HabitsRepository

class PerformHabitUseCase(private val repository: HabitsRepository) {

    suspend fun performHabit(habit : Habit, status : ConnectivityObserver.Status){
        when(status){
            ConnectivityObserver.Status.AVAILABLE -> {
                if (!habit.uid.isNullOrEmpty()){
                    repository.performHabitFromServer(habit)
                }
            }
            else -> {
                if (habit.id != null) repository.performHabitFromDatabase(habit)
            }
        }
    }
}