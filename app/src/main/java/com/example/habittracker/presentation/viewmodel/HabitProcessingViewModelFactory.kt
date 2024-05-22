package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.core.utils.NetworkConnectivityObserver
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.TestGetHabitByIdUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase

class HabitProcessingViewModelFactory(
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val updateHabitRemoteUseCase: UpdateHabitRemoteUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val nct : NetworkConnectivityObserver,
    private val testGetHabitByIdUseCase: TestGetHabitByIdUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitProcessingViewModel::class.java)) {
            HabitProcessingViewModel(
                this.updateHabitUseCase,
                this.updateHabitRemoteUseCase,
                this.createHabitUseCase,
                this.nct,
                this.testGetHabitByIdUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}