package com.example.habittracker.data.modelResponse

class HabitResponse : ArrayList<HabitItem>()

data class HabitItem(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
)