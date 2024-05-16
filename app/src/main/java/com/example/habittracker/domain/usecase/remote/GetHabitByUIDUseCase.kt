package com.example.habittracker.domain.usecase.remote

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitByUIDUseCase(private val repository: HabitsRepository)
{
    operator fun invoke(habitUID : String) : Flow<Habit> {
        return repository.getHabitItemByUID(uid = habitUID)
    }
}