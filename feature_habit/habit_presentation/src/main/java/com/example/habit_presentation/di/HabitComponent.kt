package com.example.habit_presentation.di

import android.content.Context
import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.Feature
import com.example.core.network.di.CacheModule
import com.example.core.network.di.NetworkComponent
import com.example.core.network.di.NetworkModule
import com.example.core.utils.NetworkConnectivityModule
import com.example.core.utils.NetworkConnectivityObserver
import com.example.habit_data.di.HabitDataComponent
import com.example.habit_domain.usecase.GetHabitsUseCase
import com.example.habit_presentation.presentation.ui.HabitsFragment
import com.example.habit_presentation.presentation.viewmodel.HabitsViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlin.properties.Delegates.notNull

@[Feature Component(dependencies = [HabitsDeps::class])]
interface HabitComponent {

    fun inject(fragment: HabitsFragment)

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
