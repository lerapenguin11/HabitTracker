package com.example.habittracker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.TabHabitType
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){

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
        newInstanceTabType(TabHabitType.USEFUL.type).let {
            tabAdapter.addFragment(it)
        }
        newInstanceTabType(TabHabitType.HARMFUL.type).let {
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
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val TYPE_HABITS = "type_habits"

        private fun newInstanceTabType(tabType : String) : TypeHabitsListFragment{
            val fragment = TypeHabitsListFragment()
            fragment.arguments = bundleOf(TYPE_HABITS to tabType)
            return fragment
        }
    }
}