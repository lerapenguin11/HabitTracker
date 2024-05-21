package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.core.utils.NetworkConnectivityObserver
import com.example.habittracker.domain.usecase.GetHabitsUseCase

class HabitsViewModelFactory(
    private val nct : NetworkConnectivityObserver,
    private val getHabitsUseCase: GetHabitsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            HabitsViewModel(
                this.nct,
                this.getHabitsUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}