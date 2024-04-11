package com.example.habittracker.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habittracker.presentation.model.Habit

//TODO habits_domain
interface HabitsRepository
{
    fun getHabits() : MutableLiveData<List<Habit>>

    //TODO habit_processing_domain
    fun updateHabit(habit : Habit)
    fun createHabit(newHabit: Habit)
    fun getHabitItem(habitId : Int) : Habit
}