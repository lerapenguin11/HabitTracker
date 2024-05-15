package com.example.habittracker.domain.usecase.remote

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository

class CreateHabitRemoteUseCase(private val repository : HabitsRepository)
{
    suspend operator fun invoke(newHabit : Habit) = repository.createHabitRemote(habit = newHabit)
}