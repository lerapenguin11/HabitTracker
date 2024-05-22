package com.example.habittracker.domain.usecase

import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.flow.Flow

class GetHabitByIdUseCase(private val repository : HabitsRepository) {

    fun getHabitById(uid : String?, id : Long?) : Flow<Habit> {
        return if (uid.isNullOrEmpty()){
            repository.getHabitItem(habitId = id!!)
        } else{
            repository.getHabitItemByUID(uid = uid)
        }
    }
}