package com.example.habit_data.repository

import com.example.core.network.ResultData
import com.example.core.room.HabitDao
import com.example.core.room.entity.SyncStatus
import com.example.core.utils.makeRetryingApiCall
import com.example.habit_data.api.HabitsApi
import com.example.habit_data.mappers.HabitMapper
import com.example.habit_data.mappers.HabitRemoteMapper
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitUID
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val dao : HabitDao,
    private val localMapper : HabitMapper,
    private val remoteMapper: HabitRemoteMapper,
    private val service : HabitsApi
) : HabitsRepository {

    val coroutineExceptionHandler = CoroutineExceptionHandler {
            _, throwable -> throwable.printStackTrace() }

    //TODO: пофиксить обновление
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
                    syncUploadHabitsCreate()
                    syncUploadHabitsUpdate()
                    syncServerFromDatabase()
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

    override suspend fun updateHabitFromDatabase(habit: Habit) = withContext(Dispatchers.IO) {
        dao.updateHabit(localMapper.updateHabitToHabitEntityNotSync(habit = habit))
    }

    //TODO: обновление привычки
    override suspend fun updateHabitFromServer(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO){
            try {
                val response = makeRetryingApiCall{service.editHabit(habit = remoteMapper.updateHabitToHabitItem(habit))}
                if (response.isSuccessful){
                    dao.updateHabit(
                        localMapper.updateHabitToHabitEntityRemoteTest
                            (habit = habit, uid = response.body()!!.uid)
                            .copy(syncStatus = SyncStatus.SYNCED))
                    return@withContext ResultData.Success(localMapper.habitUIDResponseToHabitUID(response.body()!!))
                }else{
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
        }

    override suspend fun createHabitFromDatabase(newHabit: Habit) = withContext(Dispatchers.IO) {
        dao.insertHabit(localMapper.insertHabitToHabitEntity(habit = newHabit)
            .copy(syncStatus = SyncStatus.PENDING_UPLOAD))
    }

    override suspend fun createHabitFromServer(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO){
            try {
                val response = makeRetryingApiCall{service.createHabit(newHabit = remoteMapper.createHabitToHabitItem(habit))}
                if (response.isSuccessful){
                    dao.insertHabit(
                        localMapper.insertHabitToHabitEntityRemoteTest(
                            habit = habit, uid = response.body()!!.uid)
                            .copy(syncStatus = SyncStatus.SYNCED))
                    return@withContext ResultData.Success(localMapper.habitUIDResponseToHabitUID(response.body()!!))
                }else{
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            }catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
        }
    //-------TODO: вынести в HabitProcessingRepositoryImpl------

    private suspend fun syncUploadHabitsCreate() = withContext(Dispatchers.IO){
        val pendingHabits = dao.getPendingUploadHabits()
        pendingHabits.forEach {habit ->
            service.createHabit(remoteMapper.habitEntityToHabitItem(habit))
            dao.updateHabit(habit.copy(syncStatus = SyncStatus.SYNCED))
        }
    }

    private suspend fun syncUploadHabitsUpdate() = withContext(Dispatchers.IO){
        val pendingHabits = dao.getPendingDownloadHabits()
        pendingHabits.forEach { habit ->
            service.editHabit(remoteMapper.habitEntityToHabitItemUpdateServer(habit))
            dao.updateHabit(habit.copy(syncStatus = SyncStatus.SYNCED))
        }
    }

    private suspend fun syncServerFromDatabase() = withContext(Dispatchers.IO){
        val habitResponse = service.getAllHabits().body()
        dao.clearAll()
        habitResponse?.forEach {
            dao.insertHabit(remoteMapper.habitItemToHabitEntity(habit = it))
        }
    }
}