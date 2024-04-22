package com.example.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository

//TODO habits_domain
class GetHabitsUseCase(private val repository : HabitsRepository)
{
    operator fun invoke() : LiveData<List<Habit>> {
        return repository.getHabits()
    }
}