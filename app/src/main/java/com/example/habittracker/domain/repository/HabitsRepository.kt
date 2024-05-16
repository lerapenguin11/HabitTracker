package com.example.habittracker.domain.repository

import com.example.habittracker.core.network.ResultData
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitUID
import kotlinx.coroutines.flow.Flow

//TODO habits_domain
interface HabitsRepository
{
    fun getHabits() : Flow<List<Habit>>
    suspend fun getHabitsRemote() : ResultData<List<Habit>>

    //TODO habit_processing_domain
    suspend fun updateHabit(habit : Habit)
    suspend fun updateHabitRemote(habit: Habit) : ResultData<HabitUID>
    suspend fun createHabit(newHabit: Habit)
    suspend fun createHabitRemote(habit : Habit) : ResultData<HabitUID>
    fun getHabitItem(habitId : String) : Flow<Habit>
    fun getHabitItemByUID(uid : String) : Flow<Habit>
}