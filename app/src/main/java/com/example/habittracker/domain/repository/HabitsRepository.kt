package com.example.habittracker.domain.repository

import androidx.lifecycle.LiveData
import com.example.habittracker.presentation.model.Habit

//TODO habits_domain
interface HabitsRepository
{
    fun getHabits() : LiveData<List<Habit>>
    fun getHabitItem(habitId : Int) : Habit

    //TODO habit_processing_domain
    fun updateHabit(habit : Habit)
    fun createHabit(habit: Habit)
}