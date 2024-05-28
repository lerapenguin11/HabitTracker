package com.example.habit_domain.di

import android.content.Context
import com.example.habit_domain.repository.HabitsRepository

interface HabitDomain {
    fun repository() : HabitsRepository

    fun context() : Context
}