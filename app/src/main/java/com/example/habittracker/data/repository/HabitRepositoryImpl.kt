package com.example.habittracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.habittracker.data.room.HabitDao
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.domain.repository.HabitsRepository
import com.example.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImpl(
    private val dao : HabitDao
) : HabitsRepository {
    private val mapper = HabitMapper()

    //TODO: обавить flow
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
        val a = dao.getHabitById(habitId)
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