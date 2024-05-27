package com.example.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.R
import com.example.core.room.entity.HabitEntity
import dagger.Module
import dagger.Provides

@Database(entities = [HabitEntity::class], version = 13, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        private var instance: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): HabitDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                context.getString(R.string.database_name)
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}