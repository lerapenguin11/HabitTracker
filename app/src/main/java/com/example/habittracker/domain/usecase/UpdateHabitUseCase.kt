package com.example.habittracker.domain.usecase

import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.presentation.model.Habit

//TODO habit_processing_domain
class UpdateHabitUseCase(private val repository: HabitsRepository)
{
    operator fun invoke(habit : Habit) = repository.updateHabit(habit = habit)
}