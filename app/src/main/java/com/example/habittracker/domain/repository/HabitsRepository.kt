package com.example.habittracker.domain.repository

import com.example.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow

//TODO habits_domain
interface HabitsRepository
{
    fun getHabits() : Flow<List<Habit>>

    //TODO habit_processing_domain
    suspend fun updateHabit(habit : Habit)
    suspend fun createHabit(newHabit: Habit)
    fun getHabitItem(habitId : Int) : Flow<Habit>
}