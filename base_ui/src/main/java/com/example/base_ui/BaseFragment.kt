package com.example.base_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

//TODO вынести в base_feature
abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}