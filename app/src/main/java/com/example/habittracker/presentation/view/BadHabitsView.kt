package com.example.habittracker.presentation.view

import androidx.core.view.isVisible
import com.example.habittracker.databinding.FragmentHabitsBadBinding
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit
import javax.security.auth.callback.Callback

class BadHabitsView(
    private val binding : FragmentHabitsBadBinding,
    private val callback: Callback
)
{
    private val adapter = HabitsAdapter()

    interface Callback {
        fun onEditHabit(habit: Habit)
    }

    init {
        adapter.onHabitListClickListener = {habit -> callback.onEditHabit(habit)}
    }

    private fun handleEmptyListMessageVisibility(habitList : List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    fun setHabitRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabitsBad.adapter = adapter
    }
}