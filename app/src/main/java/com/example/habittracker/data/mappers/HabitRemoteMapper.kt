package com.example.habittracker.data.mappers

import com.example.habittracker.data.entity.HabitEntity
import com.example.habittracker.data.modelResponse.HabitItem
import com.example.habittracker.data.modelResponse.HabitResponse
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitPriority
import com.example.habittracker.domain.model.HabitRepetitionPeriod
import com.example.habittracker.domain.model.HabitType

class HabitRemoteMapper
{
    fun habitsResponseToHabits(habitsResponse : HabitResponse) : List<Habit>{
        val habits = mutableListOf<Habit>()
        habitsResponse.forEach {response ->
            val habit = Habit(
                uid = response.uid,
                title = response.title,
                description = response.description,
                type = HabitType.codeByType(response.type),
                habitPriority = HabitPriority.codeByPriority(response.priority),
                numberExecutions = response.count,
                period = HabitRepetitionPeriod.codeByPeriod(response.frequency),
                color = response.color,
                dateCreation = response.date
            )
            habits.add(habit)
        }
        return habits
    }

    fun createHabitToHabitItem(habit : Habit) : HabitItem {
        return HabitItem(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            priority = HabitRepetitionPeriod.periodByCode(habit.period),
            count = habit.numberExecutions,
            frequency = HabitPriority.priorityByCode(habit.habitPriority),
            color = habit.color,
            date = habit.dateCreation,
            done_dates = emptyList()
        )
    }

    fun updateHabitToHabitItem(habit : Habit) : HabitItem {
        return HabitItem(
            uid = habit.uid,
            title = habit.title,
            description = habit.description,
            type = HabitType.typeByCode(habit.type),
            priority = HabitRepetitionPeriod.periodByCode(habit.period),
            count = habit.numberExecutions,
            frequency = HabitPriority.priorityByCode(habit.habitPriority),
            color = habit.color,
            date = (System.currentTimeMillis()/1000).toInt(),
            done_dates = emptyList()
        )
    }
}