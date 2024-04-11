package com.example.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.presentation.model.Habit

//TODO habits_domain
class GetHabitsUseCase(private val repository : HabitsRepository)
{
    operator fun invoke() : MutableLiveData<List<Habit>> {
        return repository.getHabits()
    }
}