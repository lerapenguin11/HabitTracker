package com.example.habittracker.presentation.view

import com.example.habittracker.presentation.model.Habit

internal interface HabitDataListener {
    fun onHabitDataReceived(habit: Habit)
}