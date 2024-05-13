package com.example.habittracker.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.databinding.ItemHabitsBinding
import com.example.habittracker.presentation.adapter.itemDiffCallback.HabitItemDiffCallback

internal class HabitsAdapter(
    var onHabitListClickListener : ((Habit) -> Unit)? = null
)
    : ListAdapter<Habit, HabitsAdapter.HabitViewHolder>(HabitItemDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val itemBinding =
            ItemHabitsBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return HabitViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.bind(habit)
    }

    inner class HabitViewHolder(
        private val binding : ItemHabitsBinding)
        : RecyclerView.ViewHolder(binding.root)
    {
        init {
            itemView.setOnClickListener {
                onHabitListClickListener?.invoke(getItem(adapterPosition))
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(habit : Habit) {
            binding.tvTitleHabit.text = habit.title
            binding.tvDescHabit.text = habit.description
            binding.chipType.text = habit.type.type
            binding.chipPriority.text = habit.habitPriority.priority
            binding.chipFrequency.text = "${habit.numberExecutions} " +
                    "${checkingNumberExclusion(habit.numberExecutions)} | ${habit.period.period}"

            //binding.habitCard.setCardBackgroundColor(habit.color)
        }

        private fun checkingNumberExclusion(numberExecutions: String): String {
            return when(numberExecutions.toInt()){
                3 -> "раза"
                else -> "раз"
            }
        }
    }
}

