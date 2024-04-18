package com.example.habittracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.data.entity.HabitEntity

@Database(entities = [HabitEntity::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {
    companion object {
        fun getInstance(context: Context) : HabitDatabase {
            return Room.databaseBuilder(context, HabitDatabase::class.java, "habit_db")
                .fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getHabitDao() : HabitDao
}