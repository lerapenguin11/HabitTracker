package com.example.habittracker.data.repository

import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.room.HabitDao
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val dao : HabitDao,
    private val mapper : HabitMapper
) : HabitsRepository {

    override fun getHabits(): Flow<List<Habit>> {
        val allHabits = dao.getDistinctAllHabits()
        return allHabits
            .map {
                element ->
            mapper.habitsEntityToHabits(element)
        }
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: Int): Flow<Habit>  {
        val habit = dao.getDistinctHabitById(habitId = habitId)
        return habit.map { mapper.habitEntityToHabit(entity = it) }
    }

    override suspend fun updateHabit(habit: Habit) = withContext(Dispatchers.IO) {
        dao.updateHabit(mapper.updateHabitToHabitEntity(habit = habit))
    }

    override suspend fun createHabit(newHabit: Habit) = withContext(Dispatchers.IO) {
        dao.insertHabit(mapper.insertHabitToHabitEntity(habit = newHabit))
    }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------
}