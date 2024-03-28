package com.example.habittracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.habittracker.databinding.FragmentAboutAppBinding
import com.example.habittracker.presentation.BaseFragment

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

}