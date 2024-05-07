package com.example.habittracker.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.habittracker.domain.model.HabitPriority
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitProcessingBinding
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.domain.model.HabitType
import com.example.habittracker.presentation.app.BaseApplication
import com.example.habittracker.presentation.view.dialog.ExecutionPeriodHabitDialog
import com.example.habittracker.presentation.view.dialog.HabitTypeDialog
import com.example.habittracker.presentation.viewmodel.HabitProcessingViewModel
import com.example.habittracker.presentation.viewmodel.HabitProcessingViewModelFactory

class HabitProcessingFragment : BaseFragment<FragmentHabitProcessingBinding>(),
    HabitTypeDialog.Host, ExecutionPeriodHabitDialog.Host {
    private var screenMode : String? = null
    private var itemArrayPriority : String? = null
    private var itemArrayExecutions : String? = null
    private var habitId : Int? = null
    private val viewModel : HabitProcessingViewModel by viewModels{
        HabitProcessingViewModelFactory(
            (requireActivity().application as BaseApplication).getHabitItemUseCase,
            (requireActivity().application as BaseApplication).createHabitUseCase,
            (requireActivity().application as BaseApplication).updateHabitUseCase
        )
    }

    private var color : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            screenMode = bundle.getString(SCREEN_MODE)
            habitId = bundle.getInt(HABIT_ID)
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
        setOnClickListener()
        initTextInputListeners()
        openHabitPriorityModal()
        openHabitRepetitionsNumberModal()
        saveHabit()
    }

    private fun setOnClickListener() {
        with(binding){
            tiEtTypeHabit.setOnClickListener {
                HabitTypeDialog().show(childFragmentManager, null) }
            tiEtFrequency.setOnClickListener {
                ExecutionPeriodHabitDialog().show(childFragmentManager, null)
            }
            btClose.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    private fun saveHabit() {
        binding.btSaveHabit.setOnClickListener {
            when(screenMode){
                MODE_ADD -> launchAddHabit(habitProcessing())
                MODE_EDIT -> launchUpdateHabit(habitProcessing())
            }
        }
    }

    private fun launchUpdateHabit(habit : Habit) {
        viewModel.updateHabit(habit = habit)
        view?.findNavController()?.popBackStack()
    }

    private fun launchAddHabit(habit : Habit) {
        viewModel.createHabit(habit = habit)
        view?.findNavController()?.popBackStack()
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
        observeHabitData()
    }

    private fun observeHabitData() = with(viewModel) {
        habitId?.let {
            loadHabitItem(habitId = habitId!!)
            habitItem.observe(viewLifecycleOwner) { habit ->
                setHabitData(habit)
            }
        }
    }

    private fun setHabitData(habit : Habit?) = with(binding) {
        habit?.let {
            binding.tiEtNameHabit.setText(habit.title)
            binding.tiEtDescHabit.setText(habit.description)
            binding.tvArrayPriority.setText(habit.habitPriority.priority)
            binding.tvArrayExecutions.setText(habit.numberExecutions)
            binding.tiEtTypeHabit.setText(habit.type.type)
            binding.tiEtFrequency.setText(habit.period.period)
            itemArrayPriority = habit.habitPriority.priority
            itemArrayExecutions = habit.numberExecutions
        }
        btSaveHabit.isEnabled = true
    }

    private fun habitProcessing() : Habit =
        with(binding){
            return Habit(
                id = getIdHabit(),
                title = tiEtNameHabit.text.toString(),
                description = tiEtDescHabit.text.toString(),
                type = getSelectedHabitType()!!,
                habitPriority = getSelectedHabitPriority()!!,
                numberExecutions = tvArrayExecutions.text.toString(),
                period = getSelectedHabitPeriod()!!,
                color = color,
                dateCreation = getDateCreationHabit()
            )
        }

    private fun getDateCreationHabit() : Long {
        return if (viewModel.habitItem.value?.dateCreation == null){
            System.currentTimeMillis()
        } else{
            viewModel.habitItem.value!!.dateCreation
        }
    }

    private fun getIdHabit(): Int {
        return if (habitId == null){
            0
        } else{
            habitId!!
        }
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

    private fun initTextInputListeners() =
        with(binding){
            val textChangedListenerAdd = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    btSaveHabit.isEnabled = checkIfFieldsNotEmpty() }

                override fun afterTextChanged(s: Editable?) {}
            }
            tilNameHabit.editText?.addTextChangedListener(textChangedListenerAdd)
            tilDescHabit.editText?.addTextChangedListener(textChangedListenerAdd)
            tilPriority.editText?.addTextChangedListener(textChangedListenerAdd)
            tilType.editText?.addTextChangedListener(textChangedListenerAdd)
            tilNumberExecutions.editText?.addTextChangedListener(textChangedListenerAdd)
            tilFrequency.editText?.addTextChangedListener(textChangedListenerAdd)
        }

    private fun checkIfFieldsNotEmpty(): Boolean =
        with(binding){
            return (tilNameHabit.editText?.length() != 0 &&
                    tilDescHabit.editText?.length() != 0 &&
                    tilPriority.editText?.length() != 0 &&
                    tilType.editText?.length() != 0 &&
                    tilNumberExecutions.editText?.length() !=0 &&
                    tilFrequency.editText?.length() != 0)
        }

    @SuppressLint("ResourceAsColor")
    private fun openHabitPriorityModal() =
        with(binding){
            val items = listOf(
                HabitPriority.HIGH.priority, HabitPriority.MEDIUM.priority, HabitPriority.LOW.priority)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_priority, items)
            tvArrayPriority.setDropDownBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_card,
                    null
                )
            )
            tvArrayPriority.threshold = 1
            tvArrayPriority.setAdapter(adapter)
            tvArrayPriority.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    itemArrayPriority = parent.getItemAtPosition(position).toString()
                }
        }

    private fun openHabitRepetitionsNumberModal() =
        with(binding){
            val items = mutableListOf<String>()
            for (i in 1 until 11){ items.add("$i") }
            val adapter = ArrayAdapter(requireContext(), R.layout.item_priority, items)
            tvArrayExecutions.threshold = 1
            tvArrayExecutions.setDropDownBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_card,
                    null
                )
            )
            tvArrayExecutions.setAdapter(adapter)
            tvArrayExecutions.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    itemArrayExecutions = parent.getItemAtPosition(position).toString()
                }
        }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val HABIT_ID = "update_habit"
    }

    override fun typeSelection(text: String) =
        with(binding){
            tiEtTypeHabit.setText(text)
        }


    override fun selectionExecutionPeriod(text: String) =
        with(binding){
            tiEtFrequency.setText(text)
    }
}