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
/*
enum class HabitPriorityEntity(val priority : String){
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий")
}

enum class HabitTypeEntity(val type : String){
    USEFUL("Полезная"),
    HARMFUL("Вредная")
}

enum class HabitRepetitionPeriodEntity(val period: String){
    REGULAR("Регулярная"),
    ONE_TIME("Разовая")
}*/
