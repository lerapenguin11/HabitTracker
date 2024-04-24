package com.example.habittracker.domain.usecase

import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.domain.model.Habit

//TODO habit_processing_domain
class CreateHabitUseCase(private val repository: HabitsRepository)
{
    suspend operator fun invoke(newHabit: Habit) = repository.createHabit(newHabit = newHabit)
}