package com.example.habittracker.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "habits", indices = [Index(value = ["id"])])
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = null,
    val title : String,
    val description : String,
    val type : String,
    val habitPriority : String,
    val numberExecutions : String,
    val period : String,
    val color : Int,
    val dateCreation : Long
)
