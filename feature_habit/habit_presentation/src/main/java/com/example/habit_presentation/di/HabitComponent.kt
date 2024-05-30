package com.example.habit_presentation.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core.Feature
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_domain.usecase.CreateHabitUseCase
import com.example.habit_domain.usecase.GetHabitByIdUseCase
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_domain.usecase.PerformHabitUseCase
import com.example.habit_domain.usecase.UpdateHabitUseCase
import com.example.habit_presentation.presentation.ui.HabitProcessingFragment
import com.example.habit_presentation.presentation.ui.HabitsFragment
import com.example.habit_presentation.presentation.ui.TypeHabitsListFragment
import com.example.habit_presentation.presentation.view.bottomSheet.ModalBottomSheet
import dagger.Component
import kotlin.properties.Delegates.notNull

@[Feature Component(dependencies = [HabitsDeps::class])]
interface HabitComponent {

    fun injectHabits(fragment: HabitsFragment)

    fun injectTypeHabits(fragment: TypeHabitsListFragment)

    fun injectModalBottomSheet(fragment : ModalBottomSheet)

    fun injectHabitProcessingFragment(fragment : HabitProcessingFragment)

    @Component.Builder
    interface Builder {

        fun deps(habitsDeps: HabitsDeps): Builder
        fun build(): HabitComponent
    }
}

interface HabitsDeps {

    val getHabitsUseCase : GetHabitsUseCase
    val ntc : NetworkConnectivityObserver
    val createHabitUseCase: CreateHabitUseCase
    val getHabitByIdUseCase: GetHabitByIdUseCase
    val updateHabitUseCase: UpdateHabitUseCase
    val performHabitUseCase : PerformHabitUseCase
}

interface HabitsDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: HabitsDeps

    companion object : HabitsDepsProvider by HabitsDepsStore
}

object HabitsDepsStore : HabitsDepsProvider {

    override var deps: HabitsDeps by notNull()
}

internal class HabitsComponentViewModel : ViewModel() {

    val newDetailsComponent =
        DaggerHabitComponent.builder().deps(HabitsDepsProvider.deps).build()
}
