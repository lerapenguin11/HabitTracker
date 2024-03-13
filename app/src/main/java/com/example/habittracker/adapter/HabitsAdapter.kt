package com.example.habittracker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.habittracker.Habits
import com.example.habittracker.R

class HabitsAdapter : ListAdapter<Habits, HabitViewHolder>(HabitItemDiffCallback())
{
    var onHabitListClickListener : ((Habits) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habits, parent, false)
        return HabitViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.titleHabit.text = habit.title
        holder.descHabit.text = habit.description
        holder.type.text = habit.type.type
        holder.priority.text = habit.priorityHabit.priority
        holder.frequency.text = "${habit.numberExecutions} ${checkingNumberExclusion(habit.numberExecutions)} | ${habit.period.period}"
        holder.itemView.setOnClickListener {
            onHabitListClickListener?.invoke(habit)
        }
    }

    private fun checkingNumberExclusion(numberExecutions: String): String{
        return when(numberExecutions.toInt()){
            3 -> CONST_TEXT_NUM_EXCEPTION
            else -> CONST_TEXT_NUM
        }
    }

    companion object{
        private val CONST_TEXT_NUM_EXCEPTION = "раза в день"
        private val CONST_TEXT_NUM = "раз в день"
    }
}