package com.example.habittracker.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Index
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
    fun getHabitById(habitId : Int) : Flow<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit : HabitEntity)

    @Update
    suspend fun updateHabit(habit : HabitEntity)

    @Delete
    suspend fun deleteHabit(habit : HabitEntity)

    /*@Query("SELECT * FROM habits WHERE id = :id")
    private fun getUserById(id: String): Flow<HabitEntity>

    fun getDistinctUserById(id: String):
            Flow<HabitEntity> = getUserById(id)
        .distinctUntilChanged()*/
}