package com.example.habittracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.data.entity.HabitEntity

@Database(entities = [HabitEntity::class], version = 4, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        private const val NAME_DB = "habit_db"
        private var instance: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): HabitDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                NAME_DB
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}