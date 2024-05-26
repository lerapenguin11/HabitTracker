package com.example.habit_presentation.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core.Feature
import com.example.habit_domain.di.HabitDomainModule
import com.example.habit_presentation.presentation.ui.HabitsFragment
import dagger.Component
import kotlin.properties.Delegates.notNull

@[Feature Component(modules = [HabitDomainModule::class])]
internal interface HabitComponent {

    fun inject(fragment: HabitsFragment)

    @Component.Builder
    interface Builder {

        fun build(): HabitComponent
    }
}

internal class ArticlesComponentViewModel : ViewModel() {

    /*val newDetailsComponent =
        DaggerArticlesComponent.builder().deps(ArticlesDepsProvider.deps).build()*/
}