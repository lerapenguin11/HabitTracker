package com.example.habittracker.data.repository

import androidx.lifecycle.LiveData
import com.example.habittracker.data.room.HabitDao
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.domain.model.Habit

class HabitRepositoryImpl(
    private val dao : HabitDao
) : HabitsRepository {
    private val mapper = HabitMapper()

    override fun getHabits(): LiveData<List<Habit>> {
        return mapper.habitsEntityToHabits(dao.getAllHabits())
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: Int): LiveData<Habit> {
        val habit = dao.getHabitById(habitId = habitId)
        return mapper.habitEntityToHabitLD(entity = habit)
    }

    override fun updateHabit(habit: Habit) {
        dao.updateHabit(mapper.habitToHabitEntity(habit = habit))
    }

    override fun createHabit(newHabit: Habit) {
        dao.insertHabit(mapper.habitToHabitEntity(habit = newHabit))
    }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------
}