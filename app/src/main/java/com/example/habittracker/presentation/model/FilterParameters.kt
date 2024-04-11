package com.example.habittracker.presentation.model

data class FilterParameters(
    var habitTitle : String?,
    val habitDescription : String?,
    val habitFrequency : HabitRepetitionPeriod?
)
