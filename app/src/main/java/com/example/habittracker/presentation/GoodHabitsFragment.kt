package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittracker.databinding.FragmentGoodHabitsBinding
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType
import com.example.habittracker.presentation.view.GoodHabitsView

class GoodHabitsFragment(private val habitList: MutableList<Habit>) : BaseFragment<FragmentGoodHabitsBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGoodHabitsBinding {
        return FragmentGoodHabitsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onHabitDataReceived()
    }

   private fun onHabitDataReceived() {
        val v = GoodHabitsView(FragmentGoodHabitsBinding.bind(requireView()))
        v.setHabitGoodRecyclerView(habitList.filter { it.type == HabitType.USEFUL })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}