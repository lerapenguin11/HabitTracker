package com.example.habittracker.presentation.view.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.R
import com.example.habittracker.databinding.LayoutModalBottomSheetBinding
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.presentation.app.BaseApplication
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.example.habittracker.presentation.viewmodel.HabitsViewModelFactory
import com.google.android.material.card.MaterialCardView

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
        setOnClickListerBtFilterDate()
        setObserverOnNameFilter()
        setObserverOnDescriptionFilter()
        setObserverOnExecutionsFilter()
        openHabitExecutions()
        setObserveOnFilterByDate()
    }

    private fun setOnClickListerBtFilterDate() = with(binding) {
        filterNewDate.setOnClickListener {
            applyStyleDateFilteringButtons(btFiltered = filterNewDate,
                btNotFiltered = filterOldDate,
                iconFiltered = icFilterNewDate,
                iconNotFiltered = icFilterOldDate)
            viewModel.searchByNewDate()
        }
        filterOldDate.setOnClickListener {
            applyStyleDateFilteringButtons(
                btFiltered = filterOldDate,
                btNotFiltered = filterNewDate,
                iconFiltered = icFilterOldDate,
                iconNotFiltered = icFilterNewDate
            )
            viewModel.searchByOldDate()
        }
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

    private fun setOnClickListener() = with(binding){
        btCancelFilter.setOnClickListener {
            filterNameHabit.text = null
            tiEtSearchDescHabit.text = null
            tvArrayExecutions.text = null
            viewModel.cancelFilter()
        }
    }

    private fun applyStyleDateFilteringButtons(
        btFiltered: MaterialCardView,
        btNotFiltered: MaterialCardView,
        iconFiltered: ImageView,
        iconNotFiltered : ImageView
    ) {
        btFiltered.strokeColor = resources.getColor(R.color.md_theme_dark_inversePrimary)
        btNotFiltered.strokeColor = resources.getColor(R.color.md_theme_light_outline)
        btFiltered.setCardBackgroundColor(resources.getColor(R.color.light_green))
        btNotFiltered.setCardBackgroundColor(resources.getColor(R.color.background_view))
        iconFiltered.setImageResource(R.drawable.ic_filtered_date)
        when(iconNotFiltered){
            binding.icFilterNewDate -> iconNotFiltered.setImageResource(R.drawable.ic_filter_new_date)
            binding.icFilterOldDate -> iconNotFiltered.setImageResource(R.drawable.ic_filter_old_date)
        }
    }

    private fun setObserveOnFilterByDate() = with(viewModel){
       filterByDate.observe(viewLifecycleOwner, Observer {
           if (it == true){
               binding.btCancelFilter.isEnabled = true
           } else{
               applyStyleDateNotFilteringButtons()
           }
       })
    }

    private fun applyStyleDateNotFilteringButtons() = with(binding) {
        filterNewDate.strokeColor = resources.getColor(R.color.md_theme_light_outline)
        filterOldDate.strokeColor = resources.getColor(R.color.md_theme_light_outline)
        filterNewDate.setCardBackgroundColor(resources.getColor(R.color.background_view))
        filterOldDate.setCardBackgroundColor(resources.getColor(R.color.background_view))
        icFilterNewDate.setImageResource(R.drawable.ic_filter_new_date)
        icFilterOldDate.setImageResource(R.drawable.ic_filter_old_date)
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