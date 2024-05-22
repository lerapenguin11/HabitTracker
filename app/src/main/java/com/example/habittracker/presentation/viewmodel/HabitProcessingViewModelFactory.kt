package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.core.utils.NetworkConnectivityObserver
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitByIdUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase

class HabitProcessingViewModelFactory(
    private val createHabitUseCase: CreateHabitUseCase,
    private val nct : NetworkConnectivityObserver,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitProcessingViewModel::class.java)) {
            HabitProcessingViewModel(
                this.createHabitUseCase,
                this.nct,
                this.getHabitByIdUseCase,
                this.updateHabitUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}