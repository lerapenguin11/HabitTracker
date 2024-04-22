package com.example.habittracker.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val id : Int,
    val title : String,
    val description : String,
    val type : HabitType,
    val habitPriority : HabitPriority,
    val numberExecutions : String,
    val period : HabitRepetitionPeriod,
    val color : Int
) : Parcelable

enum class HabitPriority(val priority : String){
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий")
}

enum class HabitType(val type : String){
    USEFUL("Полезная"),
    HARMFUL("Вредная")
}

enum class HabitRepetitionPeriod(val period: String){
    REGULAR("Регулярная"),
    ONE_TIME("Разовая")
}
