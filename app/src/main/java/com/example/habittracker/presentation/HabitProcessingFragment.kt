package com.example.habittracker.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.example.habittracker.presentation.model.HabitPriority
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitProcessingBinding
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.view.HabitDataListener
import com.example.habittracker.presentation.view.dialog.ExecutionPeriodHabitDialog
import com.example.habittracker.presentation.view.HabitProcessingView
import com.example.habittracker.presentation.view.dialog.HabitTypeDialog

class HabitProcessingFragment : BaseFragment<FragmentHabitProcessingBinding>(),
    HabitTypeDialog.Host, ExecutionPeriodHabitDialog.Host {
    private var screenMode : String? = null
    private var itemArrayPriority : String? = null
    private var itemArrayExecutions : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            screenMode = it.getString(SCREEN_MODE)
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHabitProcessingBinding {
        return FragmentHabitProcessingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleAction()
        openHabitPriorityModal()
        openHabitRepetitionsNumberModal()
        val v = HabitProcessingView(FragmentHabitProcessingBinding.bind(view),
            object : HabitProcessingView.Callback{
                override fun onTypeSelection() {
                    HabitTypeDialog().show(childFragmentManager, null)
                }

                override fun onSelectionExecutionPeriod() {
                    ExecutionPeriodHabitDialog().show(childFragmentManager, null)
                }
            })
        saveHabit(v)
    }
    private fun saveHabit(v: HabitProcessingView) {
        binding.btSaveHabit.setOnClickListener {
            when(screenMode){
                MODE_ADD -> launchAddHabit(v.createHabit())
                MODE_EDIT -> launchUpdateHabit(v.createHabit())
            }
        }
    }

    private fun launchUpdateHabit(habit : Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putParcelable(UPDATE_HABIT, habit)
        view?.findNavController()?.
        navigate(R.id.action_habitProcessingFragment_to_habitsFragment, bundle)
    }

    private fun launchAddHabit(habit : Habit) {
        val bundle = Bundle()
        bundle.putParcelable(NEW_HABIT, habit)
        bundle.putString(SCREEN_MODE, MODE_ADD)

        view?.findNavController()?.
        navigate(R.id.action_habitProcessingFragment_to_habitsFragment, bundle)
    }

    private fun handleAction() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        binding.tvTitle.setText(R.string.text_create_habit)
    }

    private fun launchEditMode() {
        binding.tvTitle.setText(R.string.text_update_habit)
        //setHabitData()
    }

    @SuppressLint("ResourceAsColor")
    private fun openHabitPriorityModal() {
        val items = listOf(
            HabitPriority.HIGH.priority, HabitPriority.MEDIUM.priority, HabitPriority.LOW.priority)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_priority, items)
        binding.tvArrayPriority.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_card,
                null
            )
        )
        binding.tvArrayPriority.threshold = 1
        binding.tvArrayPriority.setAdapter(adapter)
        binding.tvArrayPriority.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                itemArrayPriority = parent.getItemAtPosition(position).toString()
            }
    }

    private fun openHabitRepetitionsNumberModal() {
        val items = mutableListOf<String>()
        for (i in 1 until 11){ items.add("$i") }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_priority, items)
        binding.tvArrayExecutions.threshold = 1
        binding.tvArrayExecutions.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_card,
                null
            )
        )
        binding.tvArrayExecutions.setAdapter(adapter)
        binding.tvArrayExecutions.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                itemArrayExecutions = parent.getItemAtPosition(position).toString()
            }
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val NEW_HABIT = "new_habit"
        private const val UPDATE_HABIT = "update_habit"
    }

    override fun typeSelection(text: String) {
        binding.tiEtTypeHabit.setText(text)
    }

    override fun selectionExecutionPeriod(text: String) {
        binding.tiEtFrequency.setText(text)
    }
}