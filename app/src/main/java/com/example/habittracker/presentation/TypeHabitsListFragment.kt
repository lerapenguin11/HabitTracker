package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentTypeHabitsListBinding
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType

class TypeHabitsListFragment()
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private val adapter = HabitsAdapter()
    private var habitList : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val dataList = it.getParcelableArray(HABITS_LIST)
            if (dataList != null) {
                habitList = dataList.map { it as Habit }.toMutableList()
            }
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTypeHabitsListBinding {
        return FragmentTypeHabitsListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*requireActivity().supportFragmentManager.setFragmentResultListener(
            "resultList",
            viewLifecycleOwner
        ) { _, result ->
            val dataList = result.getParcelableArray("HabitsList")
            if (dataList != null) {
                habitList = dataList.map { it as Habit }.toMutableList()
            }

        }*/
        habitList.let { setHabitGoodRecyclerView(it) }
        habitClickListener()
        //setHabitGoodRecyclerView(habitList.toList())
    }

    private fun habitClickListener() {
        adapter.onHabitListClickListener = {habit ->
            openEditHabit(habit, SCREEN_MODE, MODE_EDIT)
        }
    }

    private fun setHabitGoodRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabitsGood.adapter = adapter
    }

    private fun handleEmptyListMessageVisibility(habitList : List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    private fun openEditHabit(habit: Habit, screenMode: String?, mode : String) {
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
        bundle.putParcelable(UPDATE_HABIT, habit)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvHabitsGood.adapter = null
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val SCREEN_MODE = "screen_mode"
        private const val UPDATE_HABIT = "update_habit"
        private const val HABITS_LIST = "habits_list"
    }
}