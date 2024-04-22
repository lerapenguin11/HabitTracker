package com.example.habittracker.domain.usecase

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository

class GetHabitItemUseCase(private val repository : HabitsRepository)
{
    operator fun invoke(habitId : Int) : Habit {
        return repository.getHabitItem(habitId = habitId)
    }
}