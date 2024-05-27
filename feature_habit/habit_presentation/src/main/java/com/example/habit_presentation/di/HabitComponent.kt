package com.example.habit_presentation.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core.Feature
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_domain.usecase.GetHabitsUseCase
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

    @Component.Builder
    interface Builder {

        fun deps(habitsDeps: HabitsDeps): Builder
        fun build(): HabitComponent
    }
}

interface HabitsDeps {

    val getHabitsUseCase : GetHabitsUseCase
    val ntc : NetworkConnectivityObserver
}

interface ArticlesDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: HabitsDeps

    companion object : ArticlesDepsProvider by ArticlesDepsStore
}

object ArticlesDepsStore : ArticlesDepsProvider {

    override var deps: HabitsDeps by notNull()
}

internal class ArticlesComponentViewModel : ViewModel() {

    val newDetailsComponent =
        DaggerHabitComponent.builder().deps(ArticlesDepsProvider.deps).build()
}
