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
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habittracker.presentation.model.HabitPriority
import com.example.habittracker.R
import com.example.habittracker.data.HabitRepositoryImpl
import com.example.habittracker.databinding.FragmentHabitProcessingBinding
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.presentation.model.Habit
import com.example.habittracker.presentation.model.HabitRepetitionPeriod
import com.example.habittracker.presentation.model.HabitType
import com.example.habittracker.presentation.view.dialog.ExecutionPeriodHabitDialog
import com.example.habittracker.presentation.view.dialog.HabitTypeDialog
import com.example.habittracker.presentation.viewmodel.HabitProcessingViewModel

class HabitProcessingFragment : BaseFragment<FragmentHabitProcessingBinding>(),
    HabitTypeDialog.Host, ExecutionPeriodHabitDialog.Host {
    private var screenMode : String? = null
    private var itemArrayPriority : String? = null
    private var itemArrayExecutions : String? = null
    //private var habit : Habit? = null
    private var habitId : Int? = null
    private lateinit var viewModel : HabitProcessingViewModel


    private var color : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            screenMode = bundle.getString(SCREEN_MODE)
            habitId = bundle.getInt(UPDATE_HABIT)
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
        initViewModel()
        handleAction()
        setOnClickListener()
        initTextInputListeners()
        openHabitPriorityModal()
        openHabitRepetitionsNumberModal()
        saveHabit()
    }

    private fun initViewModel() {
        val viewModelFactory = HabitProcessingViewModel.HabitProcessingViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory)[HabitProcessingViewModel::class.java]
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

    //TODO: обновление привычки
    private fun launchUpdateHabit(habit : Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putParcelable(UPDATE_HABIT, habit)
        setFragmentResult(RESULT_HABIT, bundle)
        viewModel.updateHabit(habit = habit)
        view?.findNavController()?.popBackStack()
    }
    //TODO: создание привычки
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
        setHabitData()
    }

    private fun setHabitData() {
        habitId?.let { viewModel.getHabitItem(habitId = it) }
        with(viewModel){
            binding.tiEtNameHabit.setText(habitItem.value?.title)
            binding.tiEtDescHabit.setText(habitItem.value?.description)
            binding.tvArrayPriority.setText(habitItem.value?.habitPriority?.priority)
            binding.tvArrayExecutions.setText(habitItem.value?.numberExecutions)
            binding.tiEtTypeHabit.setText(habitItem.value?.type?.type)
            binding.tiEtFrequency.setText(habitItem.value?.period?.period)
            itemArrayPriority = habitItem.value?.habitPriority?.priority
            itemArrayExecutions = habitItem.value?.numberExecutions
        }
        binding.btSaveHabit.isEnabled = true
    }

    private fun habitProcessing() : Habit {
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
        if (habitId != null){
            return habitId as Int
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
            AdapterView.OnItemClickListener { parent, _, position, _ ->
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
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                itemArrayExecutions = parent.getItemAtPosition(position).toString()
            }
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val NEW_HABIT = "new_habit"
        private const val UPDATE_HABIT = "update_habit"
        private const val RESULT_HABIT = "result_habit"
    }

    override fun typeSelection(text: String) {
        binding.tiEtTypeHabit.setText(text)
    }

    override fun selectionExecutionPeriod(text: String) {
        binding.tiEtFrequency.setText(text)
    }
}