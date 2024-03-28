package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.habittracker.R
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

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHabitsBadBinding {
        return FragmentHabitsBadBinding.inflate(inflater, container, false)
    }

    private fun onHabitDataReceived() {
        val v = BadHabitsView(FragmentHabitsBadBinding.bind(requireView()), object : BadHabitsView.Callback{
            override fun onEditHabit(habit: Habit) {
                openEditHabit(habit, SCREEN_MODE, MODE_EDIT)
            }
        })
        v.setHabitRecyclerView(habitList.filter { it.type == HabitType.HARMFUL })
    }

    override fun onResume() {
        super.onResume()
        onHabitDataReceived()
    }

    private fun openEditHabit(habit: Habit, screenMode: String?, mode: String) {
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
        bundle.putParcelable(UPDATE_HABIT, habit)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val NEW_HABIT = "new_habit"
        private const val UPDATE_HABIT = "update_habit"
    }
}