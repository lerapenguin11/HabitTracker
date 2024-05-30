package com.example.habit_presentation.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.habit_presentation.R
import com.example.habit_presentation.databinding.FragmentAboutAppBinding
import com.example.base_ui.BaseFragment

class AboutAppFragment : BaseFragment<FragmentAboutAppBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutAppBinding {
        return FragmentAboutAppBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btClose.setOnClickListener {
            it.findNavController().navigate(R.id.action_aboutAppFragment_to_habitsFragment)
        }
    }

    companion object{
        fun newInstance(): AboutAppFragment = AboutAppFragment()
    }
}