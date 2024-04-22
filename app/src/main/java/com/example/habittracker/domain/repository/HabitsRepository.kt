package com.example.habittracker.domain.repository

import androidx.lifecycle.LiveData
import com.example.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow

//TODO habits_domain
interface HabitsRepository
{
    fun getHabits() : LiveData<List<Habit>>

    //TODO habit_processing_domain
    fun updateHabit(habit : Habit)
    fun createHabit(newHabit: Habit)
    fun getHabitItem(habitId : Int) : Habit
}