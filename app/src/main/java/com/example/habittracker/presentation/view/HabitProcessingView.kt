package com.example.habittracker.presentation.view

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.findNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitProcessingBinding
import com.example.habittracker.presentation.HabitsFragment
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitPriority
import com.example.habittracker.presentation.model.HabitRepetitionPeriod
import com.example.habittracker.presentation.model.HabitType

internal class HabitProcessingView(
    private val binding : FragmentHabitProcessingBinding,
    private val callback: Callback
)
{
    private var habit : Habit? = null
    private var color : Int = 0

    interface Callback{
        fun onTypeSelection()
        fun onSelectionExecutionPeriod()
    }

    init {
        binding.tiEtTypeHabit.setOnClickListener { callback.onTypeSelection() }
        binding.tiEtFrequency.setOnClickListener { callback.onSelectionExecutionPeriod() }
        initTextInputListeners()
        binding.btClose.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    fun createHabit() : Habit { //вынести
        return Habit(
            id = getHabitId(),
            title = binding.tiEtNameHabit.text.toString(),
            description = binding.tiEtDescHabit.text.toString(),
            type = getSelectedHabitType()!!,
            habitPriority = getSelectedHabitPriority()!!,
            numberExecutions = binding.tvArrayExecutions.text.toString(),
            period = getSelectedHabitPeriod()!!,
            color = color
        )
    }

    private fun getSelectedHabitPriority() : HabitPriority?{
        return when(binding.tvArrayPriority.text.toString()){
            HabitPriority.HIGH.priority -> HabitPriority.HIGH
            HabitPriority.LOW.priority -> HabitPriority.LOW
            HabitPriority.MEDIUM.priority -> HabitPriority.MEDIUM
            else -> null
        }
    }

    private fun getSelectedHabitPeriod(): HabitRepetitionPeriod? {
        return when(binding.tiEtFrequency.text.toString()){
            HabitRepetitionPeriod.REGULAR.period -> HabitRepetitionPeriod.REGULAR
            HabitRepetitionPeriod.ONE_TIME.period -> HabitRepetitionPeriod.ONE_TIME
            else -> null
        }
    }

    private fun getSelectedHabitType() : HabitType? {
        return when(binding.tiEtTypeHabit.text.toString()){
            HabitType.USEFUL.type -> HabitType.USEFUL
            HabitType.HARMFUL.type -> HabitType.HARMFUL
            else -> null
        }
    }

    private fun getHabitId(): Int {
        if (habit != null){
            return habit!!.id
        } else{
            return (Math.random() * 100000).toInt()
        }
    }

    private fun initTextInputListeners() {
        val textChangedListenerAdd = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btSaveHabit.isEnabled = checkIfFieldsNotEmpty() }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.tilNameHabit.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilDescHabit.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilPriority.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilType.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilNumberExecutions.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.tilFrequency.editText?.addTextChangedListener(textChangedListenerAdd)
    }

    private fun checkIfFieldsNotEmpty(): Boolean {
        return (binding.tilNameHabit.editText?.length() != 0 &&
                binding.tilDescHabit.editText?.length() != 0 &&
                binding.tilPriority.editText?.length() != 0 &&
                binding.tilType.editText?.length() != 0 &&
                binding.tilNumberExecutions.editText?.length() !=0 &&
                binding.tilFrequency.editText?.length() != 0)
    }
}