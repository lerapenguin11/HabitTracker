package com.example.habittracker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habits(
    var id : Int = UNDEFINED_ID,
    val title : String,
    val description : String,
    val type : TypeHabits,
    val priorityHabit : PriorityHabit,
    val numberExecutions : String,
    val period : PeriodExecutionHabit,
    val color : Int
) : Parcelable {

    companion object{
        const val UNDEFINED_ID = -1
    }
}

enum class PriorityHabit(val priority : String){
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий")
}

enum class TypeHabits(val type : String){
    USEFUL("Полезная"),
    HARMFUL("Вредная")
}

enum class PeriodExecutionHabit(val period: String){
    REGULAR("Регулярная"),
    ONE_TIME("Разовая")
}
