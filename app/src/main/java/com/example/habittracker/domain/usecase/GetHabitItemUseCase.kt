package com.example.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.presentation.model.Habit

class GetHabitItemUseCase(private val repository : HabitsRepository)
{
    operator fun invoke(habitId : Int) : Habit {
        return repository.getHabitItem(habitId = habitId)
    }
}