package com.example.habittracker.domain.usecase.remote

import com.example.habittracker.core.network.ResultData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitsRemoteUseCase(private val repository : HabitsRepository)
{
    suspend operator fun invoke() : ResultData<List<Habit>> {
        return repository.getHabitsRemote()
    }
}