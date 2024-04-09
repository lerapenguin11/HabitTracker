package com.example.habittracker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentTypeHabitsListBinding
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.presentation.adapter.HabitsAdapter
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.TabHabitType
import com.example.habittracker.presentation.viewmodel.HabitsViewModel

class TypeHabitsListFragment()
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private lateinit var viewModel: HabitsViewModel
    private val adapter = HabitsAdapter()
    private var habitList : MutableList<Habit> = mutableListOf()
    private var habitType : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            habitType = bundle.getString(TYPE_HABITS)
            /*if (dataList != null){
                habitList = dataList.map { it as Habit }.toMutableList()
            }*/
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
        initViewModel()
        launchTypeHabit()
        setHabitsRecyclerView(habitList)
        habitClickListener()
    }

    private fun initViewModel() {
        val viewModelFactory = HabitsViewModel.HabitsViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory)[HabitsViewModel::class.java]
    }

    private fun launchTypeHabit() {
        when(habitType){
            TabHabitType.USEFUL.type -> observeHabitsUseful()
            TabHabitType.HARMFUL.type -> observeHabitsHarmful()

        }
    }

    private fun observeHabitsUseful(){
        with(viewModel){
            habitList.observe(viewLifecycleOwner, Observer { habits ->
                setHabitsRecyclerView(getUsefulHabit(habits))
            })
        }
    }

    private fun observeHabitsHarmful(){
        with(viewModel){
            habitList.observe(viewLifecycleOwner, Observer { habits ->
                setHabitsRecyclerView(getHarmfulHabit(habits))
            })
        }
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
        binding.rvHabits.adapter = null
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val SCREEN_MODE = "screen_mode"
        private const val UPDATE_HABIT = "update_habit"
        private const val HABITS_LIST = "habits_list"

        private const val TYPE_HABITS = "type_habits"
    }
}