package com.example.habittracker.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittracker.presentation.BadHabitsFragment
import com.example.habittracker.presentation.GoodHabitsFragment
import com.example.habittracker.presentation.model.Habit

class TabAdapter(fragmentActivity: FragmentActivity, private val habit : MutableList<Habit>)
    : FragmentStateAdapter(fragmentActivity) {
    private val tabCount = 2

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GoodHabitsFragment(habit)
            1 -> BadHabitsFragment(habit)
            else -> GoodHabitsFragment(habit)
        }
    }
}