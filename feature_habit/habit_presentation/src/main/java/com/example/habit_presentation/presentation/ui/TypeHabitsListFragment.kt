package com.example.habit_presentation.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.core.utils.ConnectivityObserver
import com.example.habit_domain.model.Habit
import com.example.habit_presentation.R
import com.example.habit_presentation.databinding.FragmentTypeHabitsListBinding
import com.example.habit_presentation.di.HabitsComponentViewModel
import com.example.base_ui.BaseFragment
import com.example.habit_presentation.presentation.adapter.HabitsAdapter
import com.example.habit_presentation.presentation.model.TabHabitType
import com.example.habit_presentation.presentation.viewmodel.HabitsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import kotlinx.coroutines.launch
import javax.inject.Inject

class TypeHabitsListFragment
    : BaseFragment<FragmentTypeHabitsListBinding>()
{
    private val adapter = HabitsAdapter()
    private var habitType : String? = null

    @Inject
    internal lateinit var viewModelFactory: Lazy<HabitsViewModel.Factory>

    private val viewModel: HabitsViewModel by activityViewModels {
        viewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(requireActivity())
            .get<HabitsComponentViewModel>()
            .newDetailsComponent
            .injectTypeHabits(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            habitType = bundle.getString(TYPE_HABITS)
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTypeHabitsListBinding {
        return FragmentTypeHabitsListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchTypeHabit()
        habitClickListener()
        habitDoneClickListener()
    }

    private fun habitDoneClickListener() = with(viewModel){
        adapter.onHabitDoneClickListener = {habit, position ->
            val viewHolder = binding.rvHabits.findViewHolderForAdapterPosition(position)
            inactiveButtonHabitGone(viewHolder)
            networkStatus.observe(viewLifecycleOwner) {status ->
                when(habitType){
                    TabHabitType.USEFUL.type -> { checkUsefulHabitDone(habit, status, viewHolder) }
                    TabHabitType.HARMFUL.type -> { checkHarmfulHabitDone(habit, status, viewHolder) }
                }
            }
        }
    }

    private fun activeButtonHabitGone(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder is HabitsAdapter.HabitViewHolder) {
            viewHolder.binding.btHabitGone.isEnabled = true
        }
    }

    private fun inactiveButtonHabitGone(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder is HabitsAdapter.HabitViewHolder) {
            viewHolder.binding.btHabitGone.isEnabled = false
        }
    }

    private fun checkHarmfulHabitDone( //TODO: сделать рефакторинг
        habit: Habit,
        status: ConnectivityObserver.Status?,
        viewHolder: RecyclerView.ViewHolder?
    ) {
        if (habit.done_dates.size == habit.numberExecutions){
            activeButtonHabitGone(viewHolder)
            showSnackbar(getString(R.string.text_harmful_habit_done_stop))
        }else {
            lifecycleScope.launch {
                if (viewModel.habitDone(habit, status!!)){
                    activeButtonHabitGone(viewHolder)
                    viewModel.loadHabitList(status)
                    val originalText = getString(R.string.text_remains_execute)
                    val additionalText = habit.numberExecutions - habit.done_dates.size - 1
                    showSnackbar("$originalText $additionalText")
                }
            }
        }
    }

    private fun checkUsefulHabitDone(
        habit: Habit,
        status: ConnectivityObserver.Status?,
        viewHolder: RecyclerView.ViewHolder?
    ) {
        if (habit.done_dates.size == habit.numberExecutions){
            activeButtonHabitGone(viewHolder)
            showSnackbar(getString(R.string.text_habit_done))
        } else if (habit.done_dates.size < Math.ceil(habit.numberExecutions.toDouble()/2)) {
            lifecycleScope.launch {
                if (viewModel.habitDone(habit, status!!)){
                    activeButtonHabitGone(viewHolder)
                    viewModel.loadHabitList(status)
                    val originalText = getString(R.string.text_remains_execute)
                    val additionalText = habit.numberExecutions - habit.done_dates.size - 1
                    showSnackbar("$originalText $additionalText")
                }
            }
        } else{
            lifecycleScope.launch {
                if ( viewModel.habitDone(habit, status!!)){
                    activeButtonHabitGone(viewHolder)
                    viewModel.loadHabitList(status)
                    showSnackbar(getString(R.string.text_you_breathtaking))
                }
            }
        }
    }

    private fun launchTypeHabit() {
        when(habitType){
            TabHabitType.USEFUL.type -> observeHabitsUseful()
            TabHabitType.HARMFUL.type -> observeHabitsHarmful()
        }
    }

    private fun observeHabitsUseful(){
        with(viewModel){
            loading.observe(viewLifecycleOwner) {
                if (!it) {
                    filteredUsefulHabits.observe(viewLifecycleOwner) {habits ->
                        handleEmptyListMessageVisibility(habitList = habits)
                        setHabitsRecyclerView(habits)
                    }
                }
            }
        }
    }

    private fun observeHabitsHarmful(){
        with(viewModel){
            loading.observe(viewLifecycleOwner){
                if (!it){
                    filteredHarmfulHabits.observe(viewLifecycleOwner) {habits ->
                        handleEmptyListMessageVisibility(habitList = habits)
                        setHabitsRecyclerView(habits)
                    }
                }
            }
        }
    }

    private fun habitClickListener() {
        adapter.onHabitListClickListener = {habit ->
            if (!habit.uid.isNullOrEmpty()){
                openEditHabitByUID(habit)
            } else{
                openEditHabitByID(habit)
            }
        }
    }

    private fun openEditHabitByID(habit: Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putLong(HABIT_ID, habit.id!!)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    private fun setHabitsRecyclerView(habitList : List<Habit>) = with(binding) {
        adapter.submitList(habitList)
        rvHabits.adapter = adapter
    }

    private fun handleEmptyListMessageVisibility(habitList: List<Habit>) = with(binding){
        tvTextNoHabits.isVisible = habitList.isEmpty()
    }

    private fun openEditHabitByUID(habit: Habit) {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_EDIT)
        bundle.putString(HABIT_UID, habit.uid)
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    fun showSnackbar(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.md_theme_dark_onSecondaryContainer))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_dark_surface))
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvHabits.adapter = null
    }

    companion object{
        private const val MODE_EDIT = "mode_edit"
        private const val SCREEN_MODE = "screen_mode"
        private const val HABIT_UID = "update_habit"
        private const val HABIT_ID = "id"
        private const val TYPE_HABITS = "type_habits"

        fun getInstance() : TypeHabitsListFragment = TypeHabitsListFragment()
    }
}