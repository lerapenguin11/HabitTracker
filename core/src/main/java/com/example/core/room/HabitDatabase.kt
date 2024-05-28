package com.example.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.R
import com.example.core.room.entity.HabitEntity
import com.example.core.room.typeConverter.HabitEntityTypeConverter

@Database(entities = [HabitEntity::class], version = 14, exportSchema = false)
@TypeConverters(HabitEntityTypeConverter::class)
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