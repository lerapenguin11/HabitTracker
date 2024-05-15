package com.example.habittracker.domain.usecase.local

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitItemUIDUseCase(private val repository: HabitsRepository)
{
    operator fun invoke(uid : String) : Flow<Habit> {
        return repository.getHabitItemUID(uid = uid)
    }
}