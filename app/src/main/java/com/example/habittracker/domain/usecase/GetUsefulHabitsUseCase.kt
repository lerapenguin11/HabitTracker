package com.example.habittracker.domain.usecase

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetUsefulHabitsUseCase(
    private val repository : HabitsRepository)
{
    operator fun invoke() : Flow<List<Habit>> {
        return repository.getTest("jjd")
    }
}