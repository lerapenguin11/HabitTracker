package com.example.habittracker.presentation.model

data class FilterParameters(
    var habitTitle : String?,
    var habitDescription : String?,
    var habitFrequency : Int?,
    var oldDate : Boolean?,
    var newDate : Boolean?
)
