package com.example.habittracker.presentation.view

import androidx.core.view.isVisible
import com.example.habittracker.databinding.FragmentGoodHabitsBinding
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit

class GoodHabitsView(
    private val binding : FragmentGoodHabitsBinding
)
{
    init {

    }

    private val adapter = HabitsAdapter()
    private fun handleEmptyListMessageVisibility(habitList : List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    fun setHabitGoodRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabitsGood.adapter = adapter
    }
}