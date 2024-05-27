package com.example.habit_presentation.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.habit_domain.model.Habit
import com.example.habit_presentation.R
import com.example.habit_presentation.databinding.FragmentTypeHabitsListBinding
import com.example.habit_presentation.di.ArticlesComponentViewModel
import com.example.habit_presentation.presentation.BaseFragment
import com.example.habit_presentation.presentation.adapter.HabitsAdapter
import com.example.habit_presentation.presentation.model.TabHabitType
import com.example.habit_presentation.presentation.viewmodel.HabitsViewModel
import dagger.Lazy
import javax.inject.Inject

class TypeHabitsListFragment
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private val adapter = HabitsAdapter()
    private var habitType : String? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<HabitsViewModel.Factory>

    private val viewModel: HabitsViewModel by activityViewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(requireActivity())
            .get<ArticlesComponentViewModel>()
            .newDetailsComponent
            .injectTypeHabits(this)
        super.onAttach(context)
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
            loading.observe(viewLifecycleOwner) {
                if (!it) {
                    filteredUsefulHabits.observe(viewLifecycleOwner) {
                        setHabitsRecyclerView(it)
                    }
                }
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
            if (!habit.uid.isNullOrEmpty()){
                openEditHabitByUID(habit)
            } else{
                openEditHabitByID(habit)
            }
        }
    }

    private fun openEditHabitByID(habit: Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putLong(HABIT_ID, habit.id!!)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    private fun setHabitsRecyclerView(habitList : List<Habit>) = with(binding) {
        handleEmptyListMessageVisibility(habitList = habitList)
        adapter.submitList(habitList)
        rvHabits.adapter = adapter
    }

    private fun handleEmptyListMessageVisibility(habitList: List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    private fun openEditHabitByUID(habit: Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putString(HABIT_UID, habit.uid)
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
        private const val HABIT_UID = "update_habit"
        private const val HABIT_ID = "id"
        private const val TYPE_HABITS = "type_habits"
    }
}