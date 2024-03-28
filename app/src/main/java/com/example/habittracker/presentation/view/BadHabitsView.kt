package com.example.habittracker.presentation.view

import androidx.core.view.isVisible
import com.example.habittracker.databinding.FragmentHabitsBadBinding
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit

class BadHabitsView(
    private val binding : FragmentHabitsBadBinding
)
{
    init {

    }

    private val adapter = HabitsAdapter()
    private fun handleEmptyListMessageVisibility(habitList : List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    fun setHabitRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabitsBad.adapter = adapter
    }
}