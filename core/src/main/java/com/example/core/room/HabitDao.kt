package com.example.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.room.entity.HabitEntity
import com.example.core.room.entity.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Dao
interface HabitDao
{
    @Query("SELECT * FROM habits")
    fun getAllHabits() : Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId : Long) : Flow<HabitEntity>

    @Query("SELECT * FROM habits WHERE uid = :uid")
    fun getHabitByUID(uid : String) : Flow<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit : HabitEntity)

    @Update
    suspend fun updateHabit(habit : HabitEntity)

    @Delete
    suspend fun deleteHabit(habit : HabitEntity)

    @Query("SELECT * FROM habits WHERE syncStatus = 'PENDING_UPLOAD'")
    fun getPendingUploadHabits(): List<HabitEntity>

    @Query("SELECT * FROM habits WHERE syncStatus = 'PENDING_DOWNLOAD'")
    fun getPendingDownloadHabits(): List<HabitEntity>

    @Query("DELETE FROM habits")
    suspend fun clearAll()

    fun getDistinctAllHabits():
            Flow<List<HabitEntity>> = getAllHabits()
        .distinctUntilChanged()

    fun getDistinctHabitById(habitId : Long):
            Flow<HabitEntity> = getHabitById(habitId = habitId)
        .distinctUntilChanged()

    fun getDistinctHabitByUID(uid : String):
            Flow<HabitEntity?> = getHabitByUID(uid = uid)
        .distinctUntilChanged()
}