package com.example.habittracker.presentation.adapter.itemDiffCallback

import androidx.recyclerview.widget.DiffUtil
import com.example.habittracker.domain.model.Habit

class HabitItemDiffCallback : DiffUtil.ItemCallback<Habit>()
{
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}