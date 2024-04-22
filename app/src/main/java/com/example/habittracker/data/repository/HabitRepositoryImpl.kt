package com.example.habittracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.room.HabitDao
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository

class HabitRepositoryImpl(
    private val dao : HabitDao
) : HabitsRepository {
    private val mapper = HabitMapper()

    //TODO: добавить flow
    override fun getHabits(): LiveData<List<Habit>> {
        val allHabits = dao.getAllHabits()
        return allHabits.map {
                element ->
            mapper.habitsEntityToHabits(element)
        }
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: Int): Habit {
        val habit = dao.getHabitById(habitId = habitId)
        return mapper.habitEntityToHabit(entity = habit)
    }

    override fun updateHabit(habit: Habit) {
        dao.updateHabit(mapper.updateHabitToHabitEntity(habit = habit))
    }

    override fun createHabit(newHabit: Habit) {
        dao.insertHabit(mapper.insertHabitToHabitEntity(habit = newHabit))
    }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------
}