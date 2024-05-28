package com.example.habit_domain.usecase

import com.example.core.network.ResultData
import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetHabitsUseCase(private val repository: HabitsRepository) {
    suspend fun getHabits(status : ConnectivityObserver.Status):
            Flow<ResultData<List<Habit>>> {
        return when(status){
            ConnectivityObserver.Status.AVAILABLE -> {
                val result =
                    repository.getHabitsFromServer()
                flow { emit(result) }
            }
            /*ConnectivityObserver.Status.LOSING -> {
                val result = repository.getHabitsFromServer()
                flow { emit(result) }
            }*/
            else -> {
                repository.getHabitsFromDatabase().map { ResultData.Success(it) }
            }
        }
    }
}