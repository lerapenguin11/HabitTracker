package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittracker.databinding.FragmentGoodHabitsBinding
import com.example.habittracker.databinding.FragmentHabitsBadBinding
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType
import com.example.habittracker.presentation.view.BadHabitsView
import com.example.habittracker.presentation.view.GoodHabitsView

class BadHabitsFragment(private val habitList: MutableList<Habit>) : BaseFragment<FragmentHabitsBadBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onHabitDataReceived()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHabitsBadBinding {
        return FragmentHabitsBadBinding.inflate(inflater, container, false)
    }

    private fun onHabitDataReceived() {
        val v = BadHabitsView(FragmentHabitsBadBinding.bind(requireView()))
        v.setHabitRecyclerView(habitList.filter { it.type == HabitType.HARMFUL })
    }

    companion object {

    }
}