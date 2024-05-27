package com.example.habit_presentation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_domain.usecase.GetHabitsUseCase
import javax.inject.Inject

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