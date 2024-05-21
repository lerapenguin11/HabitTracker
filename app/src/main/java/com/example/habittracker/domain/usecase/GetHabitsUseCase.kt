package com.example.habittracker.domain.usecase

import com.example.habittracker.core.network.ResultData
import com.example.habittracker.core.utils.ConnectivityObserver
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetHabitsUseCase(private val repository: HabitsRepository) {
    suspend fun getHabits(status : ConnectivityObserver.Status):
            Flow<ResultData<List<Habit>>> {
        return when(status){
            ConnectivityObserver.Status.AVAILABLE -> {
                val result = repository.getHabitsFromServer()
                flow { emit(result) }
            }
            else -> {
                repository.getHabitsFromDatabase().map { ResultData.Success(it) }
            }
        }
    }
}