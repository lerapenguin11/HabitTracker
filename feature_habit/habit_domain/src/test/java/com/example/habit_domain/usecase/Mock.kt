package com.example.habit_domain.usecase

import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitPriority
import com.example.habit_domain.model.HabitRepetitionPeriod
import com.example.habit_domain.model.HabitType

val uid = "123"
val habitId = 1L

fun getHabit() : Habit{
    return Habit("jdnjdnvjd-fdfg",
        id = 1,
        title = "122",
        description = "test",
        type = HabitType.USEFUL,
        habitPriority = HabitPriority.HIGH,
        numberExecutions = 8,
        period = HabitRepetitionPeriod.ONE_TIME,
        color = 0,
        dateCreation = 12344,
        done_dates = emptyList()
    )
}

fun getHabitIdNull() : Habit{
    return Habit("jdnjdnvjd-fdfg",
        id = null,
        title = "122",
        description = "test",
        type = HabitType.USEFUL,
        habitPriority = HabitPriority.HIGH,
        numberExecutions = 8,
        period = HabitRepetitionPeriod.ONE_TIME,
        color = 0,
        dateCreation = 12344,
        done_dates = emptyList()
    )
}

fun getHabitUidIsEmpty() : Habit{
    return Habit("",
        id = 1,
        title = "122",
        description = "test",
        type = HabitType.USEFUL,
        habitPriority = HabitPriority.HIGH,
        numberExecutions = 8,
        period = HabitRepetitionPeriod.ONE_TIME,
        color = 0,
        dateCreation = 12344,
        done_dates = emptyList()
    )
}