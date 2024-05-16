package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.domain.usecase.local.CreateHabitUseCase
import com.example.habittracker.domain.usecase.local.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.local.UpdateHabitUseCase
import com.example.habittracker.domain.usecase.remote.CreateHabitRemoteUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitByUIDUseCase
import com.example.habittracker.domain.usecase.remote.UpdateHabitRemoteUseCase

class HabitProcessingViewModelFactory(
    private val getHabitItemUseCase: GetHabitItemUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val createHabitRemoteUseCase: CreateHabitRemoteUseCase,
    private val updateHabitRemoteUseCase: UpdateHabitRemoteUseCase,
    private val getHabitByUIDUseCase: GetHabitByUIDUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitProcessingViewModel::class.java)) {
            HabitProcessingViewModel(
                this.createHabitUseCase,
                this.updateHabitUseCase,
                this.getHabitItemUseCase,
                this.createHabitRemoteUseCase,
                this.updateHabitRemoteUseCase,
                this.getHabitByUIDUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}