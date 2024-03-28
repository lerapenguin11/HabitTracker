package com.example.habittracker.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.view.HabitsView
import com.google.android.material.tabs.TabLayoutMediator

class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){
    private var screenMode : String? = null
    private var habit : Habit? = null
    private var habitList : MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            screenMode = it.getString(SCREEN_MODE)
        }

        if (savedInstanceState != null) {
            habitList = savedInstanceState.getSerializable("habitList") as MutableList<Habit>
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            habitList = savedInstanceState.getSerializable("habitList") as MutableList<Habit>
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("habitList", ArrayList(habitList))

        this.arguments = outState
        super.onSaveInstanceState(outState)
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
                    habit = it.getParcelable(NEW_HABIT)
                    habit?.let { it1 -> habitList.add(it1) }
                    val bundle = Bundle()

                    bundle.putParcelable(NEW_HABIT, habit)
                    bundle.putString(SCREEN_MODE, MODE_ADD)
                }
            }
            MODE_EDIT -> {}
        }
        setUpTabBar()
        val v = HabitsView(FragmentHabitsBinding.bind(view), object : HabitsView.Callback{
            override fun onAddHabit(mode: String, screenMode: String) {
                openAddHabit(mode, screenMode)
            }

            override fun onEditHabit(habit: Habit) {

            }

        })
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