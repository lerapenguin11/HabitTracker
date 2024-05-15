package com.example.habittracker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentTypeHabitsListBinding
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.app.BaseApplication
import com.example.habittracker.presentation.model.TabHabitType
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.example.habittracker.presentation.viewmodel.HabitsViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TypeHabitsListFragment
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private val adapter = HabitsAdapter()
    private var habitType : String? = null
    private val viewModel : HabitsViewModel by activityViewModels {
        (requireActivity().application as BaseApplication).habitsViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            habitType = bundle.getString(TYPE_HABITS)
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
        launchTypeHabit()
        habitClickListener()
    }

    override fun onResume() {
        super.onResume()
        observeHabitsUseful()
    }

    private fun launchTypeHabit() {
        when(habitType){
            TabHabitType.USEFUL.type -> observeHabitsUseful()
            TabHabitType.HARMFUL.type -> observeHabitsHarmful()
        }
    }

    private fun observeHabitsUseful(){
        with(viewModel){
            filteredUsefulHabits.observe(viewLifecycleOwner) {
                setHabitsRecyclerView(it)
            }
        }
    }

    private fun observeHabitsHarmful(){
        with(viewModel){
            filteredHarmfulHabits.observe(viewLifecycleOwner) {
                setHabitsRecyclerView(it)
            }
        }
    }

    private fun habitClickListener() {
        adapter.onHabitListClickListener = {habit ->
            openEditHabit(habit)
        }
    }

    private fun setHabitsRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabits.adapter = adapter
    }

    private fun handleEmptyListMessageVisibility(habitList: List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    private fun openEditHabit(habit: Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putString(HABIT_ID, habit.uid)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvHabits.adapter = null
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val SCREEN_MODE = "screen_mode"
        private const val HABIT_ID = "update_habit"
        private const val TYPE_HABITS = "type_habits"
    }
}