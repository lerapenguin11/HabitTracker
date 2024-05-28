package com.example.habit_presentation.presentation.model

data class FilterParameters(
    var habitTitle : String?,
    var habitDescription : String?,
    var habitFrequency : String?,
    var oldDate : Boolean?,
    var newDate : Boolean?
)
