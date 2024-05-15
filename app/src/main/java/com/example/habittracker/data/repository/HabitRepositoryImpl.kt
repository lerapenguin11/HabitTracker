package com.example.habittracker.data.repository

import com.example.habittracker.core.network.ResultData
import com.example.habittracker.data.api.HabitsApi
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.room.HabitDao
import com.example.habittracker.domain.model.Habit
import com.example.habittracker.domain.model.HabitUID
import com.example.habittracker.domain.repository.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val dao : HabitDao,
    private val mapper : HabitMapper,
    private val service : HabitsApi
) : HabitsRepository {

    override fun getHabits(): Flow<List<Habit>> {
        val allHabits = dao.getDistinctAllHabits()
        return allHabits
            .map {
                element ->
            mapper.habitsEntityToHabits(element)
        }
    }

    override suspend fun getHabitsRemote(): ResultData<List<Habit>> =
        withContext(Dispatchers.IO){
            try {
                val response = service.getAllHabits()
                if (response.isSuccessful) {
                    return@withContext ResultData.Success(mapper.habitsResponseToHabits(response.body()!!))
                } else {
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: String): Flow<Habit>  {
        val habit = dao.getDistinctHabitById(habitId = habitId)
        return habit.map { mapper.habitEntityToHabit(entity = it) }
    }

    override suspend fun updateHabit(habit: Habit) = withContext(Dispatchers.IO) {
        dao.updateHabit(mapper.updateHabitToHabitEntity(habit = habit))
    }

    override suspend fun createHabit(newHabit: Habit) = withContext(Dispatchers.IO) {
        dao.insertHabit(mapper.insertHabitToHabitEntity(habit = newHabit))
    }

    override suspend fun createHabitRemote(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO){
            try {
                val response = service.createHabit(newHabit = mapper.habitToHabitItem(habit))
                if (response.isSuccessful){
                    dao.insertHabit(mapper.insertHabitToHabitEntityRemoteTest(habit = habit, uid = response.body()!!.uid))
                    return@withContext ResultData.Success(mapper.habitUIDResponseToHabitUID(response.body()!!))
                }else{
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
        }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------
}