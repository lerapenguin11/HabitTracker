package com.example.habittracker.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.habittracker.Habits

class HabitItemDiffCallback : DiffUtil.ItemCallback<Habits>()
{
    override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
        return oldItem == newItem
    }
}