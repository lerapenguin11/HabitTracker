package com.example.habittracker.presentation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.view.HabitsView
import com.google.android.material.tabs.TabLayoutMediator

class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){
    private var screenMode : String? = null
    //private var habit : Habit? = null
    private var habitList : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            screenMode = it.getString(SCREEN_MODE)
        }
        setFragmentResultListener("habit"){requestKey, bundle ->
            val habit = bundle.getParcelable<Habit>(NEW_HABIT)
            habit?.let { habitList.add(it) }
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
        when(screenMode){
            MODE_ADD ->{
                arguments?.let {

                }
            }
            MODE_EDIT -> {
                arguments?.let {
                    /*habit = it.getParcelable(UPDATE_HABIT)
                    if (habit != null) {
                        habitList[habit!!.id] = habit!!
                    }*/
                }
            }
        }
        setUpTabBar()
        val v = HabitsView(FragmentHabitsBinding.bind(view), object : HabitsView.Callback{
            override fun onAddHabit(mode: String, screenMode: String) {
                openAddHabit(mode, screenMode)
            }

            override fun onEditHabit(habit: Habit) {
                openEditHabit(habit, SCREEN_MODE, MODE_EDIT)
            }

        })

        binding.navigationView.setupWithNavController(view.findNavController())
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inbox_item -> {
                    view.findNavController().navigate(R.id.habitsFragment)
                }
                R.id.outbox_item -> {
                    view.findNavController().popBackStack()
                    view.findNavController().navigate(R.id.aboutAppFragment)
                }
            }
            true
        }
    }

    private fun openEditHabit(habit: Habit, screenMode: String?, mode: String) {
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
        bundle.putParcelable(UPDATE_HABIT, habit)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    private fun setUpTabBar() {
        val tabAdapter = TabAdapter(requireActivity(), habitList)
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

    private fun openAddHabit(mode: String, screenMode: String) {
        val bundle = Bundle()
        bundle.putString(screenMode, mode)
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