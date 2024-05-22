package com.example.habittracker.data.repository

import com.example.habittracker.core.network.ResultData
import com.example.habittracker.core.utils.makeRetryingApiCall
import com.example.habittracker.data.api.HabitsApi
import com.example.habittracker.data.mappers.HabitMapper
import com.example.habittracker.data.mappers.HabitRemoteMapper
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
    private val localMapper : HabitMapper,
    private val remoteMapper: HabitRemoteMapper,
    private val service : HabitsApi
) : HabitsRepository {

    //TODO: доделать выгрузку
    override fun getHabitsFromDatabase(): Flow<List<Habit>> {
        val allHabits = dao.getDistinctAllHabits()
        return allHabits
            .map {
                element ->
            localMapper.habitsEntityToHabits(element)
        }
    }

    override suspend fun getHabitsFromServer(): ResultData<List<Habit>> =
        withContext(Dispatchers.IO){
            try {
                val response = makeRetryingApiCall{service.getAllHabits()}
                if (response.isSuccessful) {
                    return@withContext ResultData.Success(remoteMapper.habitsResponseToHabits(response.body()!!))
                } else {
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
    }

    //-------TODO: вынести в HabitProcessingRepositoryImpl------
    override fun getHabitItem(habitId: Long): Flow<Habit>  {
        val habit = dao.getDistinctHabitById(habitId = habitId)
        return habit.map { localMapper.habitEntityToHabit(entity = it) }
    }

    override fun getHabitItemByUID(uid: String): Flow<Habit> {
        val habit = dao.getDistinctHabitByUID(uid = uid)
        return habit.map { localMapper.habitToHabitEntityRemote( it) }
    }

    override suspend fun updateHabit(habit: Habit) = withContext(Dispatchers.IO) {
        dao.updateHabit(localMapper.updateHabitToHabitEntity(habit = habit))
    }

    //TODO: обновление привычки
    override suspend fun updateHabitRemote(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO){
            try {
                val response = service.editHabit(habit = remoteMapper.updateHabitToHabitItem(habit))
                if (response.isSuccessful){
                    dao.updateHabit(localMapper.updateHabitToHabitEntityRemoteTest(habit = habit, uid = response.body()!!.uid))
                    return@withContext ResultData.Success(localMapper.habitUIDResponseToHabitUID(response.body()!!))
                }else{
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
    }

    override suspend fun createHabit(newHabit: Habit) = withContext(Dispatchers.IO) {
        dao.insertHabit(localMapper.insertHabitToHabitEntity(habit = newHabit))
    }

    override suspend fun createHabitRemote(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO){
            try {
                val response = makeRetryingApiCall{service.createHabit(newHabit = remoteMapper.createHabitToHabitItem(habit))}
                if (response.isSuccessful){
                    dao.insertHabit(localMapper.insertHabitToHabitEntityRemoteTest(habit = habit, uid = response.body()!!.uid))
                    return@withContext ResultData.Success(localMapper.habitUIDResponseToHabitUID(response.body()!!))
                }else{
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
        }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------
}