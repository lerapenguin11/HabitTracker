package com.example.habittracker

data class Habits(
    var id : Int = UNDEFINED_ID,
    val title : String,
    val description : String,
    val type : TypeHabits,
    val priorityHabit : PriorityHabit,
    val numberExecutions : String,
    val period : PeriodExecutionHabit,
    val color : Int
){
    companion object{
        const val UNDEFINED_ID = -1
    }
}

enum class PriorityHabit(priority : String){
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий")
}

enum class TypeHabits(type : String){
    USEFUL("Полезная"),
    HARMFUL("Вредная")
}

enum class PeriodExecutionHabit(period: String){
    REGULAR("Регулярная"),
    ONE_TIME("Разовая")
}
