package com.example.habit_presentation.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitPriority
import com.example.habit_domain.model.HabitRepetitionPeriod
import com.example.habit_domain.model.HabitType
import com.example.habit_presentation.R
import com.example.habit_presentation.databinding.FragmentHabitProcessingBinding
import com.example.habit_presentation.di.ArticlesComponentViewModel
import com.example.habit_presentation.presentation.BaseFragment
import com.example.habit_presentation.presentation.view.dialog.ExecutionPeriodHabitDialog
import com.example.habit_presentation.presentation.view.dialog.HabitTypeDialog
import com.example.habit_presentation.presentation.viewmodel.HabitProcessingViewModel
import dagger.Lazy
import javax.inject.Inject

class HabitProcessingFragment : BaseFragment<FragmentHabitProcessingBinding>(),
    HabitTypeDialog.Host, ExecutionPeriodHabitDialog.Host {
    private var screenMode : String? = null
    private var itemArrayPriority : String? = null
    private var itemArrayExecutions : String? = null
    private var habitUId : String? = null
    private var habitId : Long? = null
    private var status = ConnectivityObserver.Status.UNAVAILABLE
    @Inject
    internal lateinit var viewModelFactory: Lazy<HabitProcessingViewModel.Factory>

    private val viewModel: HabitProcessingViewModel by activityViewModels {
        viewModelFactory.get()
    }

    private var color : Int = 0

    override fun onAttach(context: Context) {
        ViewModelProvider(requireActivity())
            .get<ArticlesComponentViewModel>()
            .newDetailsComponent
            .injectHabitProcessingFragment(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            screenMode = bundle.getString(SCREEN_MODE)
            habitUId = bundle.getString(HABIT_UID)
            habitId = bundle.getLong(HABIT_ID)
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
                it.findNavController().navigateUp()
            }
        }
    }

    private fun saveHabit() {
        binding.btSaveHabit.setOnClickListener {
            when(screenMode){
                MODE_ADD -> launchAddHabit(createHabitProcessing())
                MODE_EDIT -> launchUpdateHabit(updateHabitProcessing())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkNetworkStatus()
    }

    private fun checkNetworkStatus() = with(viewModel){
        networkStatus.observe(viewLifecycleOwner, Observer {
            status = it
        })
    }

    private fun launchUpdateHabit(habit : Habit) {
        viewModel.remoteUpdateHabit(habit = habit, status = status)
        view?.findNavController()?.navigateUp()
    }

    private fun launchAddHabit(habit : Habit) {
        viewModel.remoteCreateHabit(habit = habit, status)
        view?.findNavController()?.navigateUp()
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
        habitUId?.let {
            loadHabitItemById(habitUID = habitUId, habitId = habitId)
            habitItem.observe(viewLifecycleOwner) { habit ->
                setHabitData(habit)
            }
        }
        habitId?.let {
            //TODO
            loadHabitItemById(habitUID = habitUId, habitId = habitId)
            habitItem.observe(viewLifecycleOwner){habit ->
                setHabitData(habit = habit)
            }
        }
    }
    private fun setHabitData(habit : Habit?) = with(binding) {
        habit?.let {
            binding.tiEtNameHabit.setText(habit.title)
            binding.tiEtDescHabit.setText(habit.description)
            binding.tvArrayPriority.setText(habit.habitPriority.priority)
            binding.tvArrayExecutions.setText(habit.numberExecutions.toString())
            binding.tiEtTypeHabit.setText(habit.type.type)
            binding.tiEtFrequency.setText(habit.period.period)
            //itemArrayPriority = requireContext().getString(habit.habitPriority.priority)
            itemArrayExecutions = habit.numberExecutions.toString()
        }
        btSaveHabit.isEnabled = true
    }

    private fun createHabitProcessing() : Habit =
        with(binding){
            val habitPriority = tvArrayPriority.text.toString()
            val type = tiEtTypeHabit.text.toString()
            val period = tiEtFrequency.text.toString()
            return Habit(
                uid = getIdHabit(),
                title = tiEtNameHabit.text.toString(),
                description = tiEtDescHabit.text.toString(),
                type = HabitType.lineByType(type),
                habitPriority = HabitPriority.lineByPriority(habitPriority),
                numberExecutions = tvArrayExecutions.text.toString().toInt(),
                period = HabitRepetitionPeriod.lineByPeriod(period),
                color = color,
                dateCreation = (System.currentTimeMillis()/1000).toInt()
            )
        }

    private fun updateHabitProcessing() : Habit =
        with(binding){
            val habitPriority = tvArrayPriority.text.toString()
            val type = tiEtTypeHabit.text.toString()
            val period = tiEtFrequency.text.toString()
            return Habit(
                id = viewModel.habitItem.value?.id,
                uid = getIdHabit(),
                title = tiEtNameHabit.text.toString(),
                description = tiEtDescHabit.text.toString(),
                type = HabitType.lineByType(type),
                habitPriority = HabitPriority.lineByPriority(habitPriority),
                numberExecutions = tvArrayExecutions.text.toString().toInt(),
                period = HabitRepetitionPeriod.lineByPeriod(period),
                color = color,
                dateCreation = getDateCreationHabit().toInt()
            )
        }

    private fun getDateCreationHabit() : Long {
        return if (viewModel.habitItem.value?.dateCreation == null){
            System.currentTimeMillis()
        } else{
            viewModel.habitItem.value!!.dateCreation.toLong()
        }
    }

    private fun getIdHabit(): String {
        return if (habitUId == null){
            Habit.UNDEFINED_ID
        } else{
            habitUId!!
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
        private const val HABIT_UID = "update_habit"
        private const val HABIT_ID = "id"
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