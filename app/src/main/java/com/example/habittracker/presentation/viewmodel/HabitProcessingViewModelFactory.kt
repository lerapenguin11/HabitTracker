package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.core.utils.NetworkConnectivityObserver
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitByUIDUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase

class HabitProcessingViewModelFactory(
    private val getHabitItemUseCase: GetHabitItemUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val updateHabitRemoteUseCase: UpdateHabitRemoteUseCase,
    private val getHabitByUIDUseCase: GetHabitByUIDUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val nct : NetworkConnectivityObserver
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitProcessingViewModel::class.java)) {
            HabitProcessingViewModel(
                this.updateHabitUseCase,
                this.getHabitItemUseCase,
                this.updateHabitRemoteUseCase,
                this.getHabitByUIDUseCase,
                this.createHabitUseCase,
                this.nct) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}