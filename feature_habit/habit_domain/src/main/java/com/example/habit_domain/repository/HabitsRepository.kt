package com.example.habit_domain.repository

import com.example.core.network.ResultData
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitUID
import kotlinx.coroutines.flow.Flow

interface HabitsRepository
{
    fun getHabitsFromDatabase() : Flow<List<Habit>>
    suspend fun getHabitsFromServer() : ResultData<List<Habit>>
    suspend fun updateHabitFromDatabase(habit : Habit)
    suspend fun updateHabitFromServer(habit: Habit) : ResultData<HabitUID>
    suspend fun createHabitFromDatabase(newHabit: Habit)
    suspend fun createHabitFromServer(habit : Habit) : ResultData<HabitUID>
    fun getHabitItem(habitId : Long) : Flow<Habit>
    fun getHabitItemByUID(uid : String) : Flow<Habit>
    suspend fun performHabitFromServer(habit: Habit)
    suspend fun performHabitFromDatabase(habit: Habit)
}