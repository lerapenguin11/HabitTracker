package com.example.habit_data.repository

import com.example.core.network.ResultData
import com.example.core.room.HabitDao
import com.example.core.room.entity.SyncStatus
import com.example.core.utils.makeRetryingApiCall
import com.example.habit_data.api.HabitsApi
import com.example.habit_data.mappers.HabitMapper
import com.example.habit_data.mappers.HabitRemoteMapper
import com.example.habit_data.modelResponse.HabitResponse
import com.example.habit_domain.model.Habit
import com.example.habit_domain.model.HabitUID
import com.example.habit_domain.repository.HabitsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitRepositoryImpl(
    private val dao : HabitDao,
    private val localMapper : HabitMapper,
    private val remoteMapper: HabitRemoteMapper,
    private val service : HabitsApi
) : HabitsRepository {

    val coroutineExceptionHandler = CoroutineExceptionHandler {
            _, throwable -> throwable.printStackTrace() }

    override fun getHabitsFromDatabase(): Flow<List<Habit>> {
        val allHabits = dao.getDistinctAllHabits()
        return allHabits
            .map {
                    element ->
                localMapper.habitsEntityToHabits(element)
            }
    }

    override suspend fun getHabitsFromServer(): ResultData<List<Habit>> =
        withContext(Dispatchers.IO + coroutineExceptionHandler){
            try {
                val response = makeRetryingApiCall{service.getAllHabits()}
                if (response.isSuccessful) {
                    syncUploadHabitsCreate()
                    syncUploadHabitsUpdate()
                    syncServerFromDatabase(response.body()!!)
                    return@withContext ResultData.Success(remoteMapper.habitsResponseToHabits(response.body()!!))
                } else {
                    return@withContext ResultData.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext ResultData.Error(e)
            }
        }

    override fun getHabitItem(habitId: Long): Flow<Habit>  {
        val habit = dao.getDistinctHabitById(habitId = habitId)
        return habit.map { localMapper.habitEntityToHabit(entity = it) }
    }

    override fun getHabitItemByUID(uid: String): Flow<Habit> {
        val habit = dao.getDistinctHabitByUID(uid = uid)
        return habit
            .filterNotNull()
            .map {
            localMapper.habitToHabitEntityRemote(it)
        }
    }

    override suspend fun performHabitFromServer(habit: Habit) = withContext(Dispatchers.IO){
        service.doneHabit(remoteMapper.habitToHabitDoneResponse(habit.uid))
        dao.updateHabit(remoteMapper.habitToHabitDoneEntitySync(habit))
    }

    override suspend fun performHabitFromDatabase(habit: Habit) = withContext(Dispatchers.IO){
        dao.updateHabit(remoteMapper.habitToHabitDoneEntityNotSync(habit))
    }

    override suspend fun updateHabitFromDatabase(habit: Habit) = withContext(Dispatchers.IO) {
        dao.updateHabit(localMapper.updateHabitToHabitEntityNotSync(habit = habit))
    }

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

    override suspend fun createHabitFromDatabase(newHabit: Habit) = withContext(Dispatchers.IO + coroutineExceptionHandler) {
        dao.insertHabit(localMapper.insertHabitToHabitEntity(habit = newHabit)
            .copy(syncStatus = SyncStatus.PENDING_UPLOAD))
    }

    override suspend fun createHabitFromServer(habit: Habit): ResultData<HabitUID> =
        withContext(Dispatchers.IO + coroutineExceptionHandler){
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

    private suspend fun syncUploadHabitsCreate() = withContext(Dispatchers.IO + coroutineExceptionHandler){
        val pendingHabits = dao.getPendingUploadHabits()
        pendingHabits.forEach {habit ->
            service.createHabit(remoteMapper.habitEntityToHabitItem(habit))
            dao.updateHabit(habit.copy(syncStatus = SyncStatus.SYNCED))
        }
    }

    private suspend fun syncUploadHabitsUpdate() = withContext(Dispatchers.IO + coroutineExceptionHandler){
        val pendingHabits = dao.getPendingDownloadHabits()
        pendingHabits.forEach { habit ->
            service.editHabit(remoteMapper.habitEntityToHabitItemUpdateServer(habit))
            dao.updateHabit(habit.copy(syncStatus = SyncStatus.SYNCED))
        }
    }

    private suspend fun syncServerFromDatabase(habitResponse: HabitResponse) = withContext(Dispatchers.IO) {
        dao.clearAll()
        habitResponse.forEach {
            dao.insertHabit(remoteMapper.habitItemToHabitEntity(habit = it))
        }
    }
}