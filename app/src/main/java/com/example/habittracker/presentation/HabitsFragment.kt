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

    private var newHabitArgument : Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(HabitProcessingFragment.RESULT){key, bundle ->
            newHabitArgument = bundle.getParcelable<Habit>(NEW_HABIT)
            newHabitArgument?.let { habitList.add(it) }
            setUpTabLayout()
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

        val habit = arguments?.getParcelable<Habit>(NEW_HABIT)

        arguments?.let {

        }

        //TODO
        /*requireActivity().supportFragmentManager.setFragmentResultListener("result", viewLifecycleOwner
        ) { _, result ->

            *//*val bundle = Bundle()
            val habitArray = habitList.toTypedArray()
            bundle.putParcelableArray("HabitsList", habitArray)
            requireActivity().supportFragmentManager.setFragmentResult("resultList", bundle)*//*
            *//*tabAdapter.addFragment(TypeHabitsListFragment(), "Полезные", habitList.filter { it.type == HabitType.USEFUL })
            tabAdapter.addFragment(TypeHabitsListFragment(), "Вредные", habitList.filter { it.type == HabitType.HARMFUL })
            binding.viewPager.adapter = tabAdapter*//*
        }*/


        navigateNavigationView()
        setUpTabLayout()
        setOnClickListenerFabAddHabit()

        arguments?.let {
            screenMode = it.getString(SCREEN_MODE) //todo
        }

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

    }

    override fun onResume() {
        super.onResume()

    }

    private fun setOnClickListenerFabAddHabit() {
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

        //TODO


        newInstance(habitList.filter { it.type == HabitType.USEFUL })?.let {
            tabAdapter.addFragment(
                it, "Полезные")
        }
        newInstance(habitList.filter { it.type == HabitType.HARMFUL })?.let {
            tabAdapter.addFragment(
                it, "Вредные")
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

        private fun newInstance(habits: List<Habit>?): TypeHabitsListFragment? {
            val movieFragment = TypeHabitsListFragment()
            val bundle = Bundle()
            bundle.putParcelableArray(HABITS_LIST, habits?.toTypedArray())
            movieFragment.arguments = bundle
            return movieFragment
        }
    }
}