package com.example.habittracker.presentation.adapter.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip

class HabitViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    val titleHabit : TextView = view.findViewById(R.id.tv_title_habit)
    val descHabit : TextView = view.findViewById(R.id.tv_desc_habit)
    val type : Chip = view.findViewById(R.id.chip_type)
    val priority : Chip = view.findViewById(R.id.chip_priority)
    val frequency : Chip = view.findViewById(R.id.chip_frequency)
    val card : MaterialCardView = view.findViewById(R.id.habit_card)

        //add bind
}