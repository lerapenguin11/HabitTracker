package com.example.habittracker.data.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao
{
    @Query("SELECT * FROM habits")
    fun getAllHabits() : LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId : Int) : LiveData<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(habit : HabitEntity)

    @Update
    fun updateHabit(habit : HabitEntity)

    @Delete
    fun deleteHabit(habit : HabitEntity)
}