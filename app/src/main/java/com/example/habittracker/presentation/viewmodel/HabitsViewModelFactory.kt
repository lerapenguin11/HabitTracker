package com.example.habittracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.domain.usecase.local.GetHabitItemUIDUseCase
import com.example.habittracker.domain.usecase.local.GetHabitsUseCase
import com.example.habittracker.domain.usecase.remote.GetHabitsRemoteUseCase

class HabitsViewModelFactory(
    private val getHabitsUseCase : GetHabitsUseCase,
    private val getHabitsRemoteUseCase: GetHabitsRemoteUseCase,
    private val getHabitItemUIDUseCase: GetHabitItemUIDUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {

        return return if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
            HabitsViewModel(this.getHabitsUseCase,
                this.getHabitsRemoteUseCase,
                this.getHabitItemUIDUseCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}