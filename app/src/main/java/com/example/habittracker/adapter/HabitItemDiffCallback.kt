package com.example.habittracker.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.habittracker.Habit

class HabitItemDiffCallback : DiffUtil.ItemCallback<Habit>()
{
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}