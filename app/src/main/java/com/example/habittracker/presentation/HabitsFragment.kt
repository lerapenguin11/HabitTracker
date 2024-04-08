package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitType
import com.google.android.material.tabs.TabLayoutMediator

class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){
    private var screenMode : String? = null
    private var habitList : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(RESULT_HABIT){ _, bundle ->
            screenMode = bundle.getString(SCREEN_MODE)
            when(screenMode){
                MODE_EDIT -> getUpdatedHabit(bundle)
                MODE_ADD -> getNewHabit(bundle)
            }
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHabitsBinding {
        return FragmentHabitsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateNavigationView()
        setUpTabLayout()
        setOnClickListenerFabAddHabit()
    }

    private fun getUpdatedHabit(bundle: Bundle) {
        val habit = bundle.getParcelable<Habit>(UPDATE_HABIT)
        if (habit != null) {
            val habitIndex = habitList.indexOfFirst { it.id == habit.id }
            if (habitIndex != -1) {
                habitList[habitIndex] = habit
            }
            setUpTabLayout()
        }
    }

    private fun getNewHabit(bundle: Bundle) {
        bundle.getParcelable<Habit>(NEW_HABIT)?.let { habitList.add(it) }
        setUpTabLayout()
    }

    private fun setOnClickListenerFabAddHabit() { //TODO вынести fab button во фрагмент с привычками
        binding.fabAddHabits.setOnClickListener { openAddHabit(
            MODE_ADD,
            SCREEN_MODE
        ) }
    }

    private fun navigateNavigationView() = with(binding){
        view?.let { navigationView.setupWithNavController(it.findNavController()) }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inbox_item -> {
                    view?.findNavController()?.navigate(R.id.habitsFragment)
                }
                R.id.outbox_item -> {
                    view?.findNavController()?.popBackStack()
                    view?.findNavController()?.navigate(R.id.aboutAppFragment)
                }
            }
            true
        }
    }

    private fun setUpTabLayout() {
        val tabAdapter = TabAdapter(requireActivity())
        newInstance(habitList.filter { it.type == HabitType.USEFUL }).let {
            tabAdapter.addFragment(it)
        }
        newInstance(habitList.filter { it.type == HabitType.HARMFUL }).let {
            tabAdapter.addFragment(it)
        }
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayoutHabit, binding.viewPager) { tab, position ->
            when(position){
                0 -> {
                    tab.setText(R.string.text_tab1)
                    tab.setIcon(R.drawable.ic_self)
                }
                1 -> {
                    tab.setText(R.string.text_tab2)
                    tab.setIcon(R.drawable.ic_smoke)
                }
            }
        }.attach()
    }

    private fun openAddHabit(mode: String, screenMode: String) { //TODO вынести fab button во фрагмент с привычками
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
        view?.findNavController()?.saveState()
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val NEW_HABIT = "new_habit"
        private const val UPDATE_HABIT = "update_habit"
        private const val HABITS_LIST = "habits_list"
        private const val RESULT_HABIT = "result_habit"

        private fun newInstance(habits: List<Habit>?): TypeHabitsListFragment {
            val movieFragment = TypeHabitsListFragment()
            val bundle = Bundle()
            bundle.putParcelableArray(HABITS_LIST, habits?.toTypedArray())
            movieFragment.arguments = bundle
            return movieFragment
        }
    }
}