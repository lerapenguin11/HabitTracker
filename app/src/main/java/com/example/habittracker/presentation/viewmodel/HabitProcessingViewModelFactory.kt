package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.domain.usecase.CreateHabitUseCase
import com.example.habittracker.domain.usecase.GetHabitItemUseCase
import com.example.habittracker.domain.usecase.UpdateHabitUseCase

class HabitProcessingViewModelFactory(
    private val getHabitItemUseCase: GetHabitItemUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitProcessingViewModel::class.java)) {
            HabitProcessingViewModel(
                this.createHabitUseCase,
                this.updateHabitUseCase,
                this.getHabitItemUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}