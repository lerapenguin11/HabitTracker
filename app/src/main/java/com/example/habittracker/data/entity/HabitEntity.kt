package com.example.habittracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title : String,
    val description : String,
    val type : String,
    val habitPriority : String,
    val numberExecutions : String,
    val period : String,
    val color : Int
)
