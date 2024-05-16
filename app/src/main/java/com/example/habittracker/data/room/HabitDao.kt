package com.example.habittracker.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface HabitDao
{
    @Query("SELECT * FROM habits")
    fun getAllHabits() : Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId : String) : Flow<HabitEntity>

    @Query("SELECT * FROM habits WHERE uid = :uid")
    fun getHabitByUID(uid : String) : Flow<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit : HabitEntity)

    @Update
    suspend fun updateHabit(habit : HabitEntity)

    @Delete
    suspend fun deleteHabit(habit : HabitEntity)

    fun getDistinctAllHabits():
            Flow<List<HabitEntity>> = getAllHabits()
        .distinctUntilChanged()

    fun getDistinctHabitById(habitId : String):
            Flow<HabitEntity> = getHabitById(habitId = habitId)
        .distinctUntilChanged()

    fun getDistinctHabitByUID(uid : String):
            Flow<HabitEntity> = getHabitByUID(uid = uid)
        .distinctUntilChanged()

    /*@Query("SELECT * FROM habits WHERE id = :id")
    private fun getUserById(id: String): Flow<HabitEntity>

    */
}