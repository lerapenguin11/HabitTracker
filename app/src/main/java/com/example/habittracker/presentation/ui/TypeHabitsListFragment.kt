package com.example.habittracker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class TypeHabitsListFragment()
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private val adapter = HabitsAdapter()
    private var habitType : String? = null
    private val viewModel : HabitsViewModel by activityViewModels {
        HabitsViewModelFactory(
            (requireActivity().application as BaseApplication).getHabitsUseCase
        )
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

    private fun launchTypeHabit() {
        when(habitType){
            TabHabitType.USEFUL.type -> observeHabitsUseful()
            TabHabitType.HARMFUL.type -> observeHabitsHarmful()
        }
    }

    private fun observeHabitsUseful(){
        with(viewModel){
            /*viewLifecycleOwner.lifecycleScope.launch {
                filteredUsefulHabits
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
                    .distinctUntilChanged()
                    .collect {

                    }
            }*/
            filteredUsefulHabits.observe(viewLifecycleOwner, Observer {habits ->
                setHabitsRecyclerView(habits)
            })
        }
    }

    private fun observeHabitsHarmful(){
        /*with(viewModel){
            viewLifecycleOwner.lifecycleScope.launch {
                harmfulHabit
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    .distinctUntilChanged()
                    .collect { habits ->
                        setHabitsRecyclerView(habits)
                    }
            }
            *//*harmfulHabit.observe(viewLifecycleOwner, Observer {habits ->
                setHabitsRecyclerView(habits)
            })*//*
        }*/
    }

    private fun habitClickListener() {
        adapter.onHabitListClickListener = {habit ->
            openEditHabit(habit, SCREEN_MODE, MODE_EDIT)
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

    private fun openEditHabit(habit: Habit, screenMode: String?, mode : String) {
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
        bundle.putInt(HABIT_ID, habit.id)
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