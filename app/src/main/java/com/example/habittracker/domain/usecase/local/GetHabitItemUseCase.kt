package com.example.habittracker.domain.usecase.local

import androidx.lifecycle.LiveData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitItemUseCase(private val repository : HabitsRepository)
{
    operator fun invoke(habitId : String) : Flow<Habit> {
        return repository.getHabitItem(habitId = habitId)
    }
}