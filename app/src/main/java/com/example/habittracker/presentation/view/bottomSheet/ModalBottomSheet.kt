package com.example.habittracker.presentation.view.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.R
import com.example.habittracker.databinding.LayoutModalBottomSheetBinding
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.presentation.app.BaseApplication
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.example.habittracker.presentation.viewmodel.HabitsViewModelFactory

class ModalBottomSheet : Fragment()
{
    private var _binding : LayoutModalBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : HabitsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = LayoutModalBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initTextInputListeners()
        setOnClickListener()
        setObserverOnNameFilter()
        setObserverOnDescriptionFilter()
        setObserverOnExecutionsFilter()
        openHabitExecutions()
        setOnClickListener()
    }

    private fun initViewModel() {
        val getHabitsUseCase = (requireActivity().application as BaseApplication).getHabitsUseCase
        viewModel = ViewModelProvider(requireActivity(), HabitsViewModelFactory(getHabitsUseCase))[
            HabitsViewModel::class.java
        ]
    }

    private fun initTextInputListeners() =
        with(binding){
            val textChangedListenerAdd = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    btCancelFilter.isEnabled = checkIfFieldsNotEmpty() }

                override fun afterTextChanged(s: Editable?) {}
            }
            tilSearchNameHabit.editText?.addTextChangedListener(textChangedListenerAdd)
            tilSearchDescHabit.editText?.addTextChangedListener(textChangedListenerAdd)
            tilNumberExecutions.editText?.addTextChangedListener(textChangedListenerAdd)
        }

    private fun checkIfFieldsNotEmpty(): Boolean =
        with(binding){
            return (tilSearchNameHabit.editText?.length() != 0 ||
                    tilSearchDescHabit.editText?.length() != 0 ||
                    tilNumberExecutions.editText?.length() != 0)
        }

    private fun setOnClickListener() {
        binding.btCancelFilter.setOnClickListener {
            binding.filterNameHabit.text = null
            binding.tiEtSearchDescHabit.text = null
            binding.tvArrayExecutions.text = null
            viewModel.cancelFilter()
        }
    }

    private fun setObserverOnNameFilter(){
        binding.filterNameHabit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val filter = p0.toString()
                viewModel.searchByName(filter)
            }
        })
    }

    private fun setObserverOnDescriptionFilter(){
        binding.tiEtSearchDescHabit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val filter = p0.toString()
                viewModel.searchByDescription(filter)
            }
        })
    }


    private fun setObserverOnExecutionsFilter() {
        binding.tilNumberExecutions.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val filter = p0.toString()
                viewModel.searchByFrequency(filter)
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun openHabitExecutions() {
        with(binding){
            val items = listOf(
                HabitRepetitionPeriod.ONE_TIME.period, HabitRepetitionPeriod.REGULAR.period)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_priority, items)
            tvArrayExecutions.setDropDownBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_card,
                    null
                )
            )
            tvArrayExecutions.threshold = 1
            tvArrayExecutions.setAdapter(adapter)
        }
    }
}