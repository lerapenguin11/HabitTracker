package com.example.habittracker.presentation.view

import androidx.core.view.isVisible
import com.example.habittracker.databinding.FragmentGoodHabitsBinding
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit

class GoodHabitsView(
    private val binding : FragmentGoodHabitsBinding,
    private val callback: Callback
)
{
    interface Callback {
        fun onEditHabit(habit: Habit)
    }
    private val adapter = HabitsAdapter()

    init {
        adapter.onHabitListClickListener = {habit -> callback.onEditHabit(habit)}
    }

    private fun handleEmptyListMessageVisibility(habitList : List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    fun setHabitGoodRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabitsGood.adapter = adapter
    }
}