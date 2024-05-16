package com.example.habittracker.domain.usecase.remote

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository

class UpdateHabitRemoteUseCase(private val repository : HabitsRepository)
{
    suspend operator fun invoke(habit : Habit) = repository.updateHabitRemote(habit = habit)
}