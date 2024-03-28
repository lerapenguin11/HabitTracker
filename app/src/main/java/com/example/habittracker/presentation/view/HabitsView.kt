package com.example.habittracker.presentation.view

import androidx.core.view.isVisible
import com.example.habittracker.R
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.model.HabitType
import com.google.android.material.tabs.TabLayoutMediator

internal class HabitsView(
    private val binding : FragmentHabitsBinding,
    private val callback: Callback
)
{
    interface Callback{
        fun onAddHabit(mode: String, screenMode: String)
        fun onEditHabit(habit: Habit)
    }

    private val adapter = HabitsAdapter()

    init {
        binding.fabAddHabits.setOnClickListener { callback.onAddHabit(MODE_ADD, SCREEN_MODE) }
        adapter.onHabitListClickListener = {habit -> callback.onEditHabit(habit)}
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
    }
}