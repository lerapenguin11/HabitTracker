package com.example.habittracker.domain.usecase.local

import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.domain.model.Habit

//TODO habit_processing_domain
class UpdateHabitUseCase(private val repository: HabitsRepository)
{
    suspend operator fun invoke(habit : Habit) = repository.updateHabit(habit = habit)
}