package com.example.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitItemUseCase(private val repository : HabitsRepository)
{
    suspend operator fun invoke(habitId : Int) : Flow<Habit> {
        return repository.getHabitItem(habitId = habitId)
    }
}