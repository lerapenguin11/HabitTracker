package com.example.habittracker.presentation.view.bottomSheet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.databinding.LayoutModalBottomSheetBinding
import com.example.habittracker.presentation.ui.HabitsFragment
import com.example.habittracker.presentation.ui.TypeHabitsListFragment
import com.example.habittracker.presentation.view.dialog.HabitTypeDialog
import com.example.habittracker.presentation.viewmodel.HabitsViewModel

class ModalBottomSheet : Fragment()
{
    private var _binding : LayoutModalBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HabitsViewModel
    private var updateListListener: UpdateListListener? = null

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragmentManager is UpdateListListener) {
            updateListListener = childFragmentManager as UpdateListListener
        } else {
            throw RuntimeException("$childFragment must implement UpdateListListener")
        }
    }

    interface UpdateListListener {
        fun onUpdateList()
    }


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
        setObserverOnNameFilter()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btCancelFilter.setOnClickListener {
            binding.filterNameHabit.text = null
            updateListListener?.onUpdateList()
            viewModel.cancelFilter()
        }
    }



    private fun initViewModel() {
        val viewModelFactory = HabitsViewModel.HabitsViewModelFactory()
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory)[HabitsViewModel::class.java]
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

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}